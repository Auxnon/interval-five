use std::iter;

use wgpu::util::DeviceExt;
use winit::{
    event::*,
    event_loop::{ControlFlow, EventLoop},
    window::{Window, WindowBuilder},
};

#[repr(C)]
#[derive(Copy, Clone, Debug, bytemuck::Pod, bytemuck::Zeroable)]
struct Vertex {
    position: [f32; 3],
    color: [f32; 3],
}

impl Vertex {
    fn desc<'a>() -> wgpu::VertexBufferLayout<'a> {
        wgpu::VertexBufferLayout {
            array_stride: std::mem::size_of::<Vertex>() as wgpu::BufferAddress,
            step_mode: wgpu::InputStepMode::Vertex,
            attributes: &[
                wgpu::VertexAttribute {
                    offset: 0,
                    shader_location: 0,
                    format: wgpu::VertexFormat::Float32x3,
                },
                wgpu::VertexAttribute {
                    offset: std::mem::size_of::<[f32; 3]>() as wgpu::BufferAddress,
                    shader_location: 1,
                    format: wgpu::VertexFormat::Float32x3,
                },
            ],
        }
    }
}

const VERTICES: &[Vertex] = &[
    Vertex {
        position: [-0.0868241, 0.49240386, 0.0],
        color: [0.5, 0.0, 0.5],
    }, // A
    Vertex {
        position: [-0.49513406, 0.06958647, 0.0],
        color: [0.5, 0.0, 0.5],
    }, // B
    Vertex {
        position: [-0.21918549, -0.44939706, 0.0],
        color: [0.5, 0.0, 0.5],
    }, // C
    Vertex {
        position: [0.35966998, -0.3473291, 0.0],
        color: [0.5, 0.0, 0.5],
    }, // D
    Vertex {
        position: [0.44147372, 0.2347359, 0.0],
        color: [0.5, 0.0, 0.5],
    }, // E
];

const INDICES: &[u16] = &[0, 1, 4, 1, 2, 4, 2, 3, 4, /* padding */ 0];

struct State {
    surface: wgpu::Surface,
    device: wgpu::Device,
    queue: wgpu::Queue,
    sc_desc: wgpu::SwapChainDescriptor,
    swap_chain: wgpu::SwapChain,
    size: winit::dpi::PhysicalSize<u32>,
    render_pipeline: wgpu::RenderPipeline,
    // NEW!
    vertex_buffer: wgpu::Buffer,
    index_buffer: wgpu::Buffer,
    num_indices: u32,
}

impl State {
    async fn new(window: &Window) -> Self {
        let size = window.inner_size();

        // The instance is a handle to our GPU
        // BackendBit::PRIMARY => Vulkan + Metal + DX12 + Browser WebGPU
        let instance = wgpu::Instance::new(wgpu::BackendBit::PRIMARY);
        let surface = unsafe { instance.create_surface(window) };
        let adapter = instance
            .request_adapter(&wgpu::RequestAdapterOptions {
                power_preference: wgpu::PowerPreference::default(),
                compatible_surface: Some(&surface),
            })
            .await
            .unwrap();

        let (device, queue) = adapter
            .request_device(
                &wgpu::DeviceDescriptor {
                    label: None,
                    features: wgpu::Features::empty(),
                    limits: wgpu::Limits::default(),
                },
                None, // Trace path
            )
            .await
            .unwrap();

        let sc_desc = wgpu::SwapChainDescriptor {
            usage: wgpu::TextureUsage::RENDER_ATTACHMENT,
            format: adapter.get_swap_chain_preferred_format(&surface).unwrap(),
            width: size.width,
            height: size.height,
            present_mode: wgpu::PresentMode::Fifo,
        };
        let swap_chain = device.create_swap_chain(&surface, &sc_desc);

        let vs_module = device.create_shader_module(&wgpu::include_spirv!("shader.vert.spv"));
        let fs_module = device.create_shader_module(&wgpu::include_spirv!("shader.frag.spv"));
        /*
        let shader = device.create_shader_module(&wgpu::ShaderModuleDescriptor {
            label: Some("Shader"),
            flags: wgpu::ShaderFlags::all(),
            source: wgpu::ShaderSource::Wgsl(include_str!("shader.wgsl").into()),
        });*/

        let render_pipeline_layout =
            device.create_pipeline_layout(&wgpu::PipelineLayoutDescriptor {
                label: Some("Render Pipeline Layout"),
                bind_group_layouts: &[],
                push_constant_ranges: &[],
            });

        let render_pipeline = device.create_render_pipeline(&wgpu::RenderPipelineDescriptor {
            label: Some("Render Pipeline"),
            layout: Some(&render_pipeline_layout),
            vertex: wgpu::VertexState {
                //module: &shader,
                module: &vs_module,
                entry_point: "main",
                buffers: &[Vertex::desc()],
            },
            fragment: Some(wgpu::FragmentState {
                //module: &shader,
                module: &fs_module,
                entry_point: "main",
                targets: &[wgpu::ColorTargetState {
                    format: sc_desc.format,
                    blend: Some(wgpu::BlendState {
                        color: wgpu::BlendComponent::REPLACE,
                        alpha: wgpu::BlendComponent::REPLACE,
                    }),
                    write_mask: wgpu::ColorWrite::ALL,
                }],
            }),
            primitive: wgpu::PrimitiveState {
                topology: wgpu::PrimitiveTopology::TriangleList,
                strip_index_format: None,
                front_face: wgpu::FrontFace::Ccw,
                cull_mode: Some(wgpu::Face::Back),
                // Setting this to anything other than Fill requires Features::NON_FILL_POLYGON_MODE
                polygon_mode: wgpu::PolygonMode::Fill,
                // Requires Features::DEPTH_CLAMPING
                clamp_depth: false,
                // Requires Features::CONSERVATIVE_RASTERIZATION
                conservative: false,
            },
            depth_stencil: None,
            multisample: wgpu::MultisampleState {
                count: 1,
                mask: !0,
                alpha_to_coverage_enabled: false,
            },
        });

        let vertex_buffer = device.create_buffer_init(&wgpu::util::BufferInitDescriptor {
            label: Some("Vertex Buffer"),
            contents: bytemuck::cast_slice(VERTICES),
            usage: wgpu::BufferUsage::VERTEX,
        });
        let index_buffer = device.create_buffer_init(&wgpu::util::BufferInitDescriptor {
            label: Some("Index Buffer"),
            contents: bytemuck::cast_slice(INDICES),
            usage: wgpu::BufferUsage::INDEX,
        });
        let num_indices = INDICES.len() as u32;

        Self {
            surface,
            device,
            queue,
            sc_desc,
            swap_chain,
            size,
            render_pipeline,
            vertex_buffer,
            index_buffer,
            num_indices,
        }
    }

    fn resize(&mut self, new_size: winit::dpi::PhysicalSize<u32>) {
        self.size = new_size;
        self.sc_desc.width = new_size.width;
        self.sc_desc.height = new_size.height;
        self.swap_chain = self.device.create_swap_chain(&self.surface, &self.sc_desc);
    }

    #[allow(unused_variables)]
    fn input(&mut self, event: &WindowEvent) -> bool {
        false
    }

    fn update(&mut self) {}

    fn render(&mut self) -> Result<(), wgpu::SwapChainError> {
        let frame = self.swap_chain.get_current_frame()?.output;

        let mut encoder = self
            .device
            .create_command_encoder(&wgpu::CommandEncoderDescriptor {
                label: Some("Render Encoder"),
            });

        {
            let mut render_pass = encoder.begin_render_pass(&wgpu::RenderPassDescriptor {
                label: Some("Render Pass"),
                color_attachments: &[wgpu::RenderPassColorAttachment {
                    view: &frame.view,
                    resolve_target: None,
                    ops: wgpu::Operations {
                        load: wgpu::LoadOp::Clear(wgpu::Color {
                            r: 0.1,
                            g: 0.2,
                            b: 0.3,
                            a: 1.0,
                        }),
                        store: true,
                    },
                }],
                depth_stencil_attachment: None,
            });

            render_pass.set_pipeline(&self.render_pipeline);
            render_pass.set_vertex_buffer(0, self.vertex_buffer.slice(..));
            render_pass.set_index_buffer(self.index_buffer.slice(..), wgpu::IndexFormat::Uint16);
            render_pass.draw_indexed(0..self.num_indices, 0, 0..1);
        }

        self.queue.submit(iter::once(encoder.finish()));

        Ok(())
    }
}

fn main() {
    env_logger::init();
    let event_loop = EventLoop::new();
    let window = WindowBuilder::new().build(&event_loop).unwrap();

    use futures::executor::block_on;

    // Since main can't be async, we're going to need to block
    let mut state = block_on(State::new(&window));

    event_loop.run(move |event, _, control_flow| {
        match event {
            Event::WindowEvent {
                ref event,
                window_id,
            } if window_id == window.id() => {
                if !state.input(event) {
                    match event {
                        WindowEvent::CloseRequested => *control_flow = ControlFlow::Exit,
                        WindowEvent::KeyboardInput { input, .. } => match input {
                            KeyboardInput {
                                state: ElementState::Pressed,
                                virtual_keycode: Some(VirtualKeyCode::Escape),
                                ..
                            } => *control_flow = ControlFlow::Exit,
                            _ => {}
                        },
                        WindowEvent::Resized(physical_size) => {
                            state.resize(*physical_size);
                        }
                        WindowEvent::ScaleFactorChanged { new_inner_size, .. } => {
                            // new_inner_size is &mut so w have to dereference it twice
                            state.resize(**new_inner_size);
                        }
                        _ => {}
                    }
                }
            }
            Event::RedrawRequested(_) => {
                state.update();
                match state.render() {
                    Ok(_) => {}
                    // Recreate the swap_chain if lost
                    Err(wgpu::SwapChainError::Lost) => state.resize(state.size),
                    // The system is out of memory, we should probably quit
                    Err(wgpu::SwapChainError::OutOfMemory) => *control_flow = ControlFlow::Exit,
                    // All other errors (Outdated, Timeout) should be resolved by the next frame
                    Err(e) => eprintln!("{:?}", e),
                }
            }
            Event::MainEventsCleared => {
                // RedrawRequested will only trigger once, unless we manually
                // request it.
                window.request_redraw();
            }
            _ => {}
        }
    });
}

/*
package interval;

import intervalEntity.Player;
import intervalEntity.EntityWander;
import intervalEntity.MouseSelector;
import intervalStructure.Building;
import intervalGui.*;
import java.io.IOException;
import java.util.Random;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;*/

//TODO SKEPTIC GAME ENGINE?

/*yo dawg, i herd u like time so we got you 
 * this time so you could go back in time then 
 * forward in time and then watch yourself 
 * in real time and backwards time and timey wimey time dawg*/

//major 0 minor 01 build 2 release pa

/*SPECS
 * @author Aninon
 *
 * max sanity size 4096 x 4096  
 * 
 * max aging size 1024 x 1024
 *
 *method pattern:
 * logic() every tick
 * time() every time-tick (can vary from game tick rate)
 * render() every rendered tick
 * annual() called each time the timeline passes to a new cell
 * 
 * setTime() called to all instances when the timeline is moved
 * predict() method called to estimate the chain of actions taken over a specified time span
 * activate() called to activate the object
 * Destroy() called upon termination of an object 
 *
 */


	/*public static Sound sound;
	public static boolean RUN=true;
	public static int FPS=60;
	public static float zoom;
	public static MouseSelector mouseSelector;
	public static float Wx=-32f;
	public static float Wy=-32f;
	public static float Wz=0f;
	public static float panSpeed=0f;
	public static Player player;
	public static Player reservePlayer;
	public static World world;
	static int timer=0;
	static boolean escapeLock=false;
	public static PlayerPastManager past;
	public static Level level;
	public static Random rand;
	public static Controls control;
	//public static ModelTerraBuffer technicalModel;
	public static  MainFrame mainFrame=new MainFrame();
	
	public static Loader loader;
	public static Sync sync;*/
	/*
	public static void main(String[] arg) throws IOException{
		sound=new Sound();
		
		lastFPS = getTime();
		mainFrame.init();
		Render.init();
		
		ModelManager.init();
		loader = new Loader();
		sync=new Sync();
		sync.init();
		loader.start();
		//technicalModel = Model.hoopla();
		TextureCache.init();
		control = new Controls();
		world = new World();
		mouseSelector = new MouseSelector();
		zoom=10f;
		rand=new Random();
		LevelManager.init();
		level=new Level(0);
		past=new PlayerPastManager();
		player=new Player();
		player.renewSchedule();
		world.init();
		float zoomSpeed=0f;
		FileManager.init();
		Changer.initialGui();
		
	

		int sdffh=0;
		int n=3;
		MainFrame.screenAutoScale();

		boolean tHook=false;

		boolean nLock=false;
		boolean f3Lock=false;
		while(RUN){
			control.run();
			panSpeed=-0.1f;

			sync.run();

			if(Render.getGui() instanceof GuiTitle){
				if(sdffh>0){
					sdffh--;
				}else if(Keyboard.isKeyDown(Keyboard.KEY_P)){
					//Main.world.wipeP();
					sdffh=30;
					//sound.runInstance(sound.ocar1);
					//Render.inventoryDive();
					sound.runInstance(sound.coin);
				}else if(Keyboard.isKeyDown(Keyboard.KEY_L)){
					sdffh=8;
					//sound.runInstance(sound.bwom);
					Music.test.renew();
					world.addS(new Building(mouseSelector.x,mouseSelector.y,0));
				}else{
					if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
						n++;
						if(n>7){
							n=0;
						}
						sdffh=20;
					}
					if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)){
						n--;
						if(n<0){
							n=7;
						}
						sdffh=20;
					}
				}
				

				if(Keyboard.isKeyDown(Keyboard.KEY_1)){
					//sound.playNote("C#"+n,10);
					//sound.boop();
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_2)){
					sound.playNote("D"+n,10);
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_3)){
					sound.playNote("D#"+n,10);
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_4)){
					sound.playNote("E"+n,10);
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_5)){
					sound.playNote("F"+n,10);
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_6)){
					sound.playNote("F#"+n,10);
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_7)){
					sound.playNote("G"+n,10);
				}
				
				if(Keyboard.isKeyDown(Keyboard.KEY_8)){
					sound.playNote("G#"+n,10);
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_9)){
					sound.playNote("A"+n,10);
				}
				
				
				
				if(Keyboard.isKeyDown(Keyboard.KEY_M)){
					if(!tHook){
						SoundFX.piff.play();
					}
					tHook=true;
				}else
					tHook=false;

			}
			if(Keyboard.isKeyDown(Keyboard.KEY_F3)){
				if(!f3Lock)
				if(Render.getGui().isHidden())Render.getGui().unhide();else Render.getGui().hide();
				f3Lock=true;
			}else f3Lock=false;
				
				
			
			if(Keyboard.isKeyDown(Keyboard.KEY_N)){
				if(!nLock){
				Main.world.land.sloped=!Main.world.land.sloped;
				Main.world.land.renewSegments();}
				nLock=true;
			}else{
				nLock=false;
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_H))
				UserData.setTimeRate(0.1);
			
			if(Keyboard.isKeyDown(Keyboard.KEY_G))
				UserData.setTimeRate(1);

			sound.run();
			if(Music.test!=null)
				Music.test.run();

			if(!Render.getGui().isMenu()){
				if(Keyboard.isKeyDown(Keyboard.KEY_ADD)){ //
					zoom-=0.05f;
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT)){ //
					zoom+=0.05f;
				}
				if(control.sprint){
					zoomSpeed+=Mouse.getDWheel()/100f;
				}else{
					zoomSpeed+=Mouse.getDWheel()/1000f;
				}
				//if(Keyboard.isKeyDown(Keyboard.KEY_N)){ 
				// 	EntityWander w= new EntityWander();
				// 	w.setPos(mouseSelector.x,mouseSelector.y,mouseSelector.z);
				// 	world.addE(w);
				// }
				
			}
			timer++;

			if(Math.abs(zoomSpeed)>0.01f){
				zoomSpeed/=1.1f;
				zoom+=zoomSpeed;
			}else{
				zoomSpeed=0f;
			}
			if(zoom<2f){
				zoom=2f;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_H)){
				Changer.gc();
			}
			// if(Keyboard.isKeyDown(Keyboard.KEY_B)){
			// 	Changer.blank();
			// }
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) ){
				if(!escapeLock){
					Changer.togglePause();
					escapeLock=true;
				}
			}else {
				escapeLock=false;
			}
			if (Display.isCloseRequested()) {
				Changer.destroy();
			}

			if(!UserData.paused){
				if(ModelManager.updated)
					world.modelUpdate();
				world.logic();
				player.logic();
				if(world.timeline.isActive()){
					if(!UserData.timeFreeze){
						world.time();
					}
					player.time(world.timeline);
				}
			}
			if(!Render.getGui().isMenu()){
				Wx=-player.x;
				Wy=-player.y;
				Wz=-player.z-2f;
			}
			if(Render.getGui().isGame())
				level.test();
			
			Render.cycle();
			updateFPS();
			Display.update();
			Display.sync(Main.FPS);
		}

	}
	public static void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			MainFrame.set("Interval     FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}
	static int fps;
	static long lastFPS;

	public static long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

}*/
