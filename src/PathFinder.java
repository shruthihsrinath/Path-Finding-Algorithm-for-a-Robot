package ISCAssignment;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Math;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;
import java.awt.BorderLayout;

import javax.swing.*;

/*To find the shortest path from the start to the goal using A* algorithm and avoiding obstacles
 * Using the backward chaining technique where the goal is already known and the expert system attempts to
 * reach the goal hence also called as the goal driven reasoning or inference .*/
public class PathFinder extends JPanel {
	private static final long serialVersionUID = 1L;

		//A array to store and display the final path chosen
		static int follow[][];
		
		//Array to store the input
		static int input[][];
		
		/* A array to store the details of each node in the grid
		path[i][j][0] - x axis 
		path[i][j][1] - y axis
		path[i][j][2] - angle
		path[i][j][3] - 1 if the way is not blocked , 0 if the way is blocked.
		path[i][j][4] - whether the path is visited or not
		path[i][j][5] - the path count */
		static int path[][][];
		
		//Array to store the cost of each node
		static double cost[][];
		
		//To find the nodes of the shortest path selected
		static int node = 1;
		
		//Count of the number of ways being searched to reach the goal
		static int count = 1;
		
		//Each tile size in the grid
		private final int TILE_SIZE = 25;
		
		//Number of tiles in the grid
		static int TILE_COUNT = 0;
		
		//array to find whether the node is loaded or not
		private final boolean[][] loaded;
		
		//array to say that the node is loading
		private final boolean[][] loading;

		//Constructor for the class PathFinder
		public PathFinder() {
			//To set the dimensions of the frame for the display
		        setPreferredSize(new Dimension(
		                TILE_SIZE * TILE_COUNT, TILE_SIZE * TILE_COUNT));
		        
		    //To set the background of the frame to black
		        setBackground(Color.BLACK);
		        
		        loaded = new boolean[TILE_COUNT][TILE_COUNT];
		        loading = new boolean[TILE_COUNT][TILE_COUNT];
		}
		 
		//main function
		public static void main(String[] args) {
			/* size of the environment 
			ex - environment x axis
			ey - environment y axis */
			int ex,ey,tokencount = 0;
			/* sx - start point x axis 
			sy - start point y axis
			gx - goal point x axis
			gy - goal point y axis */
			int sx = 1,sy = 1,gx = 1,gy = 1;
			//input string from the input file
			String in;
			
			String outputfile="outputfile.txt";
			try {
		        //Instantiate the BufferedReader Class
		        BufferedReader bufferReader = new BufferedReader(new FileReader("inputfile.txt"));
		        
   		        //Read the size of the environment
				ex = Integer.parseInt(bufferReader.readLine());
				ey = Integer.parseInt(bufferReader.readLine());
				
				input = new int [ex][ey];
				path = new int [ex][ey][6];
				cost = new double [ex][ey];
				follow = new int[ex*ey][3];
				TILE_COUNT = ex;
				
				//Read the start point and the end point
				sx = Integer.parseInt(bufferReader.readLine());
				sy = Integer.parseInt(bufferReader.readLine());
				gx = Integer.parseInt(bufferReader.readLine());
				gy = Integer.parseInt(bufferReader.readLine());
				
				//Read the environment
				for (int i = 0 ; i < ex ; i++){
					in = bufferReader.readLine();
					StringTokenizer strToken = new StringTokenizer(in);
					tokencount = strToken.countTokens();
					for(int j = 0 ; j <  tokencount ; j++)
					{
						input[i][j] = Integer.parseInt((String)strToken.nextElement());
					}
					
				}
				
				bufferReader.close();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				System.out.println("Please enter the input");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Please enter the input");
			}
				//initialize all the nodes in the grid not blocked and distance to 1
				for (int i = 0 ; i < path.length ; i++){
					for (int j = 0 ; j < path[i].length ; j++){
							cost[i][j] = 1;
							path[i][j][3] = 1;
					}
				}
				
				//transfer the data from input array to path array
				for (int i = 0 ; i < input.length ; i++){
					for (int j = 0 ; j < input[i].length ; j++){
						if(input[i][j] == 0){
							path[i][j][3] = 0;
						}
					}
				}
				
				//Call the function rules
				Rules(sx,sy,gx,gy,sx,sy);
				
				//GUI
				if(count < 300){
					follow[0][0] = sx;
					follow[0][1] = sy;
					for (int i = 0 ; i < path.length ; i++){
						for (int j = 0 ; j < path[i].length ; j++){
							if(path[i][j][5] != 0)
							{
								
								follow[path[i][j][5]][0] = i;
								follow[path[i][j][5]][1] = j;
								follow[path[i][j][5]][2] = path[i][j][2];
								node = node + 1;
							}
						}
					}
					
			 try {
				 			//Display the nodes of the shortest path in a file
				            FileWriter fw = new FileWriter (outputfile);
				            BufferedWriter bw = new BufferedWriter (fw);
				            PrintWriter fileOut = new PrintWriter (bw); 
				            fileOut.println ("The shortest path is :"); 
				            for (int i = 0 ; i <node; i++){
				            	if(follow[i][2] == 0)
				            		fileOut.print("move south ");
				            	if(follow[i][2] == 45)
				            		fileOut.print("move south east ");
				            	if(follow[i][2] == 90)
				            		fileOut.print("move east ");
				            	if(follow[i][2] == 135)
				            		fileOut.print("move north east ");
				            	if(follow[i][2] == 180)
				            		fileOut.print("move north ");
				            	if(follow[i][2] == 225)
				            		fileOut.print("move north west ");
				            	if(follow[i][2] == 270)
				            		fileOut.print("move west ");
				            	if(follow[i][2] == 315)
				            		fileOut.print("move south west ");
				            	if(follow[i][2] == 360)
				            		fileOut.print("move south ");
								fileOut.println(follow[i][0]+ " " + follow[i][1]);
							}
				            fileOut.close();
				  }
			 catch (Exception e){
				            System.out.println("Couldn't write to the file");
			 }      
					
					EventQueue.invokeLater(new Runnable() {
			            public void run() {
			            	try {
			                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
			                }
			            	
			                
			                JFrame frame = new JFrame("Tiles");
			                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			                frame.setLayout(new BorderLayout());
			                frame.add(new PathFinder());
			                frame.pack();
			                frame.setLocationRelativeTo(null);
			                frame.setVisible(true);
			            }
			        });
				}
				else
					System.out.println("Sorry the path cannot be found");
		}
		
		/* compute the distance(Heuristic Diagonal Distance) between the target node and 
		the current node to choose the shortest path */
		public static double Heuristiccostance(int targetx,int targety,int currentx,int currenty,double cost){
			double d = 0;
			double dx = 0,dy = 0;
			dx = Math.abs(currentx - targetx);
			dy = Math.abs(currenty - targety);
			d = cost + Math.sqrt(dx * dx + dy * dy);
			return d;
		}
		
		/* Store the nodes selected for the shortest path in the path array */
		public static void MakePath(int newx,int newy,int oldx,int oldy,int angle,double costance){
			path[newx][newy][0] = oldx;
			path[newx][newy][1] = oldy;
			path[newx][newy][2] = angle;
			path[newx][newy][4] = 1;
			path[newx][newy][5] = path[oldx][oldy][5] + 1;
		}
		
		/* Set of paths to be followed for the computation of the 
		  shortest path from the current node to the destination */
		public static void Rules(int sx,int sy,int gx,int gy,int initx,int inity){
				int newx = 0,newy = 0,f = 0;
				//tempd - temporary distance , d - final distance 
				double d = 1,tempd = 0;
				int flag = 0,angle = 0,len = path.length;
				if(sx != gx || sy != gy){
					//match the rules with the data in the database
					// If the angle of the robot is 0deg or 45deg or 315deg
					if(sy+1<len && (path[sx][sy][2] == 0 ||path[sx][sy][2] == 45 ||path[sx][sy][2] == 315 ||path[sx][sy][2] == 360) 
							&& path[sx][sy+1][3] == 1 && path[sx][sy+1][4] == 0 && f == 0)
					{
						if(sy+1 == gy){
							if(sx == gx){
								newx = sx;
								newy = sy+1;
								angle = 0;
								f = 1;
							}
						}
						else
						{
							tempd = Heuristiccostance(gx,gy,sx,sy+1,cost[sx][sy]);
							if(tempd < d || d == 1)
							{
								newx = sx; 
								newy = sy+1;
								angle = 0;
								d = tempd;
								flag = 1;
							}
						}
					}

					// If the angle of the robot is 0deg or 45deg or 90deg
					if(sx+1<len && sy+1<len && (path[sx][sy][2] == 0 ||path[sx][sy][2] == 45 ||path[sx][sy][2] == 90||path[sx][sy][2] == 360) 
							&& path[sx+1][sy+1][3] == 1 && path[sx+1][sy+1][4] == 0 && f == 0)
					{
						if(sx+1 == gx){
							if(sy+1 == gy){
								newx = sx+1;
								newy = sy+1;
								angle = 45;
								f = 1;
							}
						}
						else
						{
							tempd = Heuristiccostance(gx,gy,sx+1,sy+1,cost[sx][sy]);
							if(tempd < d || d == 1)
							{
								newx = sx+1; 
								newy = sy+1;
								angle = 45;
								d = tempd;
								flag = 1;
							}
						}
					}

					// If the angle of the robot is 45deg or 90deg or 135deg
					if(sx+1<len && (path[sx][sy][2] == 45 ||path[sx][sy][2] == 90 ||path[sx][sy][2] == 135) 
							&& path[sx+1][sy][3] == 1  && path[sx+1][sy][4] == 0 && f == 0)
					{
						if(sx+1 == gx){
							if(sy == gy){
								newx = sx+1;
								newy = sy;
								angle = 90;
								f = 1;
							}
						}
						else
						{
							tempd = Heuristiccostance(gx,gy,sx+1,sy,cost[sx][sy]);
							if(tempd < d || d == 1)
							{
								newx = sx+1; 
								newy = sy;
								angle = 90;
								d = tempd;
								flag = 1;
							}
						}
					}

					// If the angle of the robot is 90deg or 135deg or 180deg
					if(sx+1<len && sy-1>-1 && (path[sx][sy][2] == 90 ||path[sx][sy][2] == 135 ||path[sx][sy][2] == 180) 
							&& path[sx+1][sy-1][3] == 1  && path[sx+1][sy-1][4] == 0 && f == 0)
					{
						if(sx+1 == gx){
							if(sy-1 == gy){
								newx = sx+1; 
								newy = sy-1;
								angle = 135;
								f = 1;
							}
						}
						else
						{
							tempd = Heuristiccostance(gx,gy,sx+1,sy-1,cost[sx][sy]);
							if(tempd < d || d == 1)
							{
								newx = sx+1; 
								newy = sy-1;
								angle = 135;
								d = tempd;
								flag = 1;
							}
						}
					}

					// If the angle of the robot is 225deg or 180deg or 135deg
					if(sy-1>-1 && (path[sx][sy][2] == 225 ||path[sx][sy][2] == 180 ||path[sx][sy][2] == 135) 
							&& path[sx][sy-1][3] == 1 && path[sx][sy-1][4] == 0 && f == 0)
					{
						if(sx == gx){
							if(sy-1 == gy){
								newx = sx;
								newy = sy-1;
								angle = 180;
								f = 1;
							}
						}
						else
						{
							tempd = Heuristiccostance(gx,gy,sx,sy-1,cost[sx][sy]);
							if(tempd < d || d == 1)
							{
								newx = sx; 
								newy = sy-1;
								angle = 180;
								d = tempd;
								flag = 1;
							}
						}
					}

					// If the angle of the robot is 270deg or 225deg or 180deg
					if(sx-1>-1 && sy-1>-1 && (path[sx][sy][2] == 270 ||path[sx][sy][2] == 225 ||path[sx][sy][2] == 180) 
							&& path[sx-1][sy-1][3] == 1  && path[sx-1][sy-1][4] == 0 && f == 0)
					{
						if(sx-1 == gx){
							if(sy-1 == gy){
								newx = sx-1; 
								newy = sy-1;
								angle = 225;
								f = 1;
							}
						}
						else{
							tempd = Heuristiccostance(gx,gy,sx-1,sy-1,cost[sx][sy]);
							if(tempd < d || d == 1)
							{
								newx = sx-1; 
								newy = sy-1;
								angle = 225;
								d = tempd;
								flag = 1;
							}
						}
						
					}

					// If the angle of the robot is 315deg or 270deg or 225deg
					if(sx-1>-1 && (path[sx][sy][2] == 315 ||path[sx][sy][2] == 270 ||path[sx][sy][2] == 225) 
							&& path[sx-1][sy][3] == 1  && path[sx-1][sy][4] == 0 && f == 0)
					{
						if(sx-1 == gx){
							if(sy == gy){
								newx = sx-1;
								newy = sy;
								angle = 270;
								f = 1;
							}
						}
						else
						{
							tempd = Heuristiccostance(gx,gy,sx-1,sy,cost[sx][sy]);
							if(tempd < d || d == 1)
							{
								newx = sx-1; 
								newy = sy;
								angle = 270;
								d = tempd;
								flag = 1;
							}
						}
					}
					// If the angle of the robot is 0deg or 315deg or 270deg
					if(sx-1>-1 && sy+1<len && (path[sx][sy][2] == 0 ||path[sx][sy][2] == 315 ||path[sx][sy][2] == 270||path[sx][sy][2] == 360) 
							&& path[sx-1][sy+1][3] == 1 && path[sx-1][sy+1][4] == 0 && f == 0)
					{
						if(sy+1 == gy){
							if(sx-1 == gx){
								newx = sx-1;
								newy = sy+1;
								angle = 315;
								f = 1;
							}
						}
						else
						{
							tempd = Heuristiccostance(gx,gy,sx-1,sy+1,cost[sx][sy]);
							if(tempd < d || d == 1)
							{
								newx = sx-1; 
								newy = sy+1;
								angle = 315;
								d = tempd;
								flag = 1;
							}
						}
					}
					
					//confliction resolution where one of the rule which matches the fact is selected
					if(count > 300)
						return;
					if(count < 300)
					{
						//If any of the node is selected then store it in path and call the function again with the selected node
						if(flag == 1 || f == 1){
							count = count + 1;
							MakePath(newx,newy,sx,sy,angle,d);
							Rules(newx,newy,gx,gy,initx,inity);
						}
						//If none of the node is selected then backtrack to one node before and search again for the new path
						else
						{
							if(sx == initx && sy == inity){
								count = count + 1;
								if(path[sx][sy][2] != 360)
									path[sx][sy][2] = path[sx][sy][2]+45;
								else
									path[sx][sy][2] = path[sx][sy][2]-45;
								//fire or action the action of the rule is executed and the cursor goes to the first phase.
								Rules(sx,sy,gx,gy,initx,inity);
							}
							else{
								count = count + 1;
								path[sx][sy][5] = 0;
								Rules(path[sx][sy][0],path[sx][sy][1],gx,gy,initx,inity);
							}
						}
					}
				}
			}
		
		/* GUI part for the Algorithm */
		//Function to get each tile at a time
	    public boolean getTile(final int x, final int y,int c) {
	    	boolean canPaint = loaded[x][y];
	    	if(count < 300){
	            if(!canPaint && !loading[x][y]) {
	                loading[x][y] = true;
	                Timer timer = new Timer(c,
	                        new ActionListener() {
	                    @Override
	                    public void actionPerformed(ActionEvent e) {
	                        loaded[x][y] = true;
	                        repaint(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
	                    }
	                });
	                timer.setRepeats(true);
	                timer.start();
	            }
	    	}
	    	return canPaint;
	    }

	    //Function to paint the component (The Frame)
	    @Override
	    protected void paintComponent(Graphics g) {
	    	if(count < 300){
	    		super.paintComponent(g);

	            Rectangle clip = g.getClipBounds();
	            int startX = clip.x - (clip.x % TILE_SIZE);
	            int startY = clip.y - (clip.y % TILE_SIZE);
	            int count = 1;
	          
	            //Display the Tiles which are blocked in RED color
	            for(int x = startX; x < clip.x + clip.width; x += TILE_SIZE) {
		            for(int y = startY; y < clip.y + clip.height; y += TILE_SIZE) {
		                if(path[x / TILE_SIZE][y / TILE_SIZE][3] == 0) {
		                	g.setColor(Color.RED);
		                	g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
		                }
		            }
		        }
	            g.setColor(Color.BLUE);
				g.fillRect(follow[0][0] * TILE_SIZE,follow[0][1] * TILE_SIZE, TILE_SIZE, TILE_SIZE);
	            
	            g.setColor(Color.GREEN);
				g.fillRect(follow[node-1][0] * TILE_SIZE,follow[node-1][1] * TILE_SIZE, TILE_SIZE, TILE_SIZE);
	            
	             //Display the Tiles of the shortest path in green color
	            for (int k = 1 ; k <node ; k++){
	            	for(int x = startX; x < clip.x + clip.width; x += TILE_SIZE) {
	            		for(int y = startY; y < clip.y + clip.height; y += TILE_SIZE) {
	            			if(follow[k][0] == x/TILE_SIZE && follow[k][1] == y/TILE_SIZE)
	            			{
	            				if(getTile(x / TILE_SIZE, y / TILE_SIZE,count  = count +500)) {
	            					g.setColor(Color.YELLOW);
	            					g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
	            				}
	            			}
	            		}
	            	}
	            }
	    	}
	    	
	    }
	}

