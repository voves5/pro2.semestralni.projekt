package cz.uhk.fim.game.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.Timer;

import cz.uhk.fim.pro2.game.model.Bird;
import cz.uhk.fim.pro2.game.model.Heart;
import cz.uhk.fim.pro2.game.model.Tube;
import cz.uhk.fim.pro2.game.model.World;
import interfaces.WorldListener;

public class GameScreen extends Screen  implements WorldListener{
	
	private long lastTimeMillis;
	private Timer timer;

	public GameScreen(MainFrame mainFrame) {
		super(mainFrame);
		
		JButton jButtonBack = new JButton("BACK");
		JButton jButtonPause = new JButton("PAUSE");
		
		jButtonBack.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.setScreen(new HomeScreen(mainFrame));
			}
		});
		
		jButtonPause.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(timer.isRunning() == true){
					timer.stop();
				}else{
					lastTimeMillis = System.currentTimeMillis();
					timer.start();
				}
			}
		});
		
		jButtonBack.setBounds(20, 20, 60, 60);
		jButtonBack.setFont(new Font("Arial",Font.PLAIN, 8));
		jButtonPause.setBounds(400, 20, 60, 60);
		jButtonPause.setFont(new Font("Arial",Font.PLAIN, 8));
		
		add(jButtonPause);
		add(jButtonBack);
		
		//WORLD	
		Bird bird = new Bird("Pavel", 150, 400);
		
		World world = new World(bird, this);
		world.addTubet(new Tube(400, 400, Color.green));
		world.addTubet(new Tube(600, 300, Color.green));
		world.addTubet(new Tube(800, 500, Color.green));
		
		world.addHeart(new Heart(500, 450));
		world.addHeart(new Heart(700, 600));
		
		GameCanvas gamecanvas = new GameCanvas(world);
		gamecanvas.setBounds(0, 0, MainFrame.WIDTH, MainFrame.HEIGHT);
		
		gamecanvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				bird.goUp();
			}
		});
		
		add(gamecanvas);


		timer = new Timer(20, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				long currentTimeMillis = System.currentTimeMillis();
				
				float delta = (currentTimeMillis - lastTimeMillis) / 1000f;				
				world.update(delta);
				gamecanvas.repaint();
				
				lastTimeMillis = currentTimeMillis;
			}
		});
		
		lastTimeMillis = System.currentTimeMillis();
		timer.start();
	}

	@Override
	public void crashTube(Tube tube) {
		System.out.println("naboural do tuby");
		
	}

	@Override
	public void catchHeart(Heart heart) {
		System.out.println("sebral srdce");
		
	}

	@Override
	public void outOf() {
		System.out.println("je mimo");
		
	}
}

