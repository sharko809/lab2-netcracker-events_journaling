package com.sharko.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class LifeSim extends JFrame {
	 
	private static final long	serialVersionUID	= 3400265056061021539L;
 
	private LifePanel			lifePanel			= null;
	private JButton				button1				= null;
	private JButton				button2				= null;
	private JSlider				slider				= null;
 
	public LifeSim(String title) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		lifePanel = new LifePanel();
		lifePanel.initialize(100, 70);
		add(lifePanel);
 
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		add(toolBar, BorderLayout.NORTH);
 
		button1 = new JButton("Çàïóñòèòü");
		toolBar.add(button1);
		button2 = new JButton("Î÷èñòèòü ïîëå");
		toolBar.add(button2);
 
		slider = new JSlider(1, 200);
		slider.setValue(50);
		lifePanel.setUpdateDelay(slider.getValue());
		slider.addChangeListener(new ChangeListener() {
			@Override public void stateChanged(ChangeEvent e) {
				lifePanel.setUpdateDelay(slider.getValue());
			}
		});
 
		toolBar.addSeparator();
		toolBar.add(new JLabel(" Áûñòðî"));
		toolBar.add(slider);
		toolBar.add(new JLabel("Ìåäëåííî"));
 
		button1.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				if (lifePanel.isSimulating()) {
					lifePanel.stopSimulation();
					button1.setText("Çàïóñòèòü");
				} else {
					lifePanel.startSimulation();
					button1.setText("Îñòàíîâèòü");
				}
			}
		});
		button2.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				synchronized (lifePanel.getLifeModel()) {
					lifePanel.getLifeModel().clear();
					lifePanel.repaint();
				}
			}
		});
		button1.setMaximumSize(new Dimension(100, 50));
		button2.setMaximumSize(new Dimension(100, 50));
		slider.setMaximumSize(new Dimension(300, 50));
		pack();
		setVisible(true);
	}

 
}
