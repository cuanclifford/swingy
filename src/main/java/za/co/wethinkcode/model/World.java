package za.co.wethinkcode.model;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;

public class World {
	
	@NotNull
	private int size;
	
	private ArrayList<Enemy> enemies;
	
	@NotNull
	private Character[][] board;
	
	private Enemy.EnemyBuilder enemyBuilder;
	
	public Hero hero;
	
	private Random random;
	
	public World(Hero hero) {
		this.hero = hero;
		this.enemies = new ArrayList<Enemy>();
		this.random = new Random();
		this.generateWorld();
	}
	
	public int getSize() { return this.size; }
	
	public Character[][] getBoard() { return this.board; }
	
	public void updateBoard() {
		// clear the board
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				this.board[i][j] = null;
			}
		}
		
		// set the hero position on the board
		this.board[this.hero.coordinates.getY()][this.hero.coordinates.getX()] = this.hero;
		
		for (Enemy enemy : this.enemies) {
			this.board[enemy.coordinates.getY()][enemy.coordinates.getX()] = enemy;
		}
	}
	
	public Character boardAt(int x, int y) {
		return this.board[y][x];
	}
	
	public void removeEnemy(Enemy enemy) {
		if (this.enemies.contains(enemy))
			this.enemies.remove(enemy);
	}
	
	public void moveEnemies() {
		int heroX = this.hero.coordinates.getX();
		int heroY = this.hero.coordinates.getY();
		
		int minX = -1;
		int minY = -1;
		
		for (Enemy enemy : this.enemies) {
			double minDistance = Integer.MAX_VALUE; 
						
			for (int i = enemy.coordinates.getY() - 1; i < 3; i++) {
				for (int j = enemy.coordinates.getX() - 1; j < 3; j++) {
					double distance = Math.sqrt((j - heroX) * (j - heroX) + (i - heroY) * (i - heroY));
					if (this.board[i][j] == null && distance < minDistance) {
						minDistance = distance;
						minY = i;
						minX = j;
					}
				}
			}
			
			if (minDistance < Integer.MAX_VALUE && minX != -1 && minY != -1) {
				enemy.coordinates.setX(minX);
				enemy.coordinates.setY(minY);
				this.updateBoard();
			}
		}
	}
	
	public void generateWorld() {
		int level = this.hero.getLevel();
		
		// calculate size of map from hero level
		this.size = (level - 1) * 5 + 10 - (level % 2);
		
		// set player in the center of the board
		int halfSize = (int)Math.ceil(this.size / 2);
		this.hero.coordinates = new Coordinates(halfSize, halfSize);
		
		this.board = new Character[this.size][this.size];
		this.updateBoard();
		
		// TODO: give enemies different statistics based on level
		this.enemyBuilder = new Enemy.EnemyBuilder("Generic Enemy")
		.health(20)
		.attack(1)
		.defense(0);
		
		this.enemies.clear();
		
		// 6.6% of the map will be filled with enemies
		int enemyCount = (int)(this.size * this.size * 0.066);
		
		// create enemies at random locations on the map
		for (int i = 0; i < enemyCount; i++) {
			int randX = random.nextInt(this.size);
			int randY = random.nextInt(this.size);
			while (this.board[randX][randY] != null) {
				randX = random.nextInt(this.size);
				randY = random.nextInt(this.size);
			}
			this.enemies.add(this.enemyBuilder.coordinates(randY, randX).build());
			this.updateBoard();
		}
		
	}

}
