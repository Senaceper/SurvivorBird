package com.senaceper.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

public class SurvivorBird extends ApplicationAdapter {

	SpriteBatch batch; // nesneleri oluşturmak için kuşu düşmanı gibi.
	Texture background; //objenin imajını aldığı patern.
	Texture bird;
	Texture enemy1;
	Texture enemy2;
	Texture enemy3;
	Texture enemy4;
	float birdx=0;
	float birdy=0;
	int gameState=0;
	float velocity=0;
	float gravity=0.2f;
	float enemyVelocity=2;
	Random random;
	int score=0;
	int scoredEnemy=0;

	BitmapFont font;

	Circle birdcircle;

	ShapeRenderer shapeRenderer;

	Circle[] enemycircles;
	Circle[] enemycircles2;
	Circle[] enemycircles3;
	Circle[] enemycircles4;

	int numberOfEnemies=5;
	float [] enemyX=new float[numberOfEnemies];
	float distance=0;

	float[] enemyOffSet=new float[numberOfEnemies];
	float[] enemyOffSet2=new float[numberOfEnemies];
	float[] enemyOffSet3=new float[numberOfEnemies];
	float[] enemyOffSet4= new float[numberOfEnemies];


	@Override
	public void create () {
		batch= new SpriteBatch();
		background= new Texture("background.png");
		bird=new Texture("bird.png");
		enemy1= new Texture("enemy.png");
		enemy2= new Texture("enemy.png");
		enemy3= new Texture("enemy.png");
		enemy4= new Texture("enemy.png");

		distance= Gdx.graphics.getWidth()/2;
		random= new Random();

		birdx=Gdx.graphics.getWidth()/2-bird.getHeight()/2;
		birdy=Gdx.graphics.getHeight()/3;

		font= new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);

		shapeRenderer= new ShapeRenderer();

		birdcircle= new Circle();
		enemycircles= new Circle[numberOfEnemies];
		enemycircles2= new Circle[numberOfEnemies];
		enemycircles3= new Circle[numberOfEnemies];
		enemycircles4= new Circle[numberOfEnemies];

		for(int i=0; i<numberOfEnemies; i++)
		{
			enemyOffSet[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight());
			enemyOffSet2[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight());
			enemyOffSet3[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight());
			enemyOffSet4[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight());

			enemyX[i]=Gdx.graphics.getWidth()-enemy1.getWidth()/2+i*distance;


			enemycircles[i]= new Circle();
			enemycircles2[i]= new Circle();
			enemycircles3[i]= new Circle();
			enemycircles4[i]= new Circle();

		}

	}

	@Override
	public void render () {//oyun devam ederken sürekli çağırılan methoddur.

		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if(gameState==1)
		{
			if(enemyX[scoredEnemy]< Gdx.graphics.getWidth()/2-bird.getHeight()/2)
			{
				score++;

				if(scoredEnemy<numberOfEnemies-1)
				{
					scoredEnemy++;
				}
				else
				{
					scoredEnemy=0;
				}
			}



			if(Gdx.input.justTouched()){
				velocity=-4;
			}

			for(int i=0;i<numberOfEnemies;i++)
			{
				if(enemyX[i]<Gdx.graphics.getWidth()/14)
				{
					enemyOffSet[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight());
					enemyOffSet2[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight());
					enemyOffSet3[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight());
					enemyOffSet4[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight());
					enemyX[i]=enemyX[i]+numberOfEnemies*distance;
				}
				else {
					enemyX[i]= enemyX[i]-enemyVelocity;
				}

				batch.draw(enemy1,enemyX[i],Gdx.graphics.getHeight()/3+enemyOffSet[i],Gdx.graphics.getWidth()/14,Gdx.graphics.getHeight()/9);
				batch.draw(enemy2,enemyX[i],Gdx.graphics.getHeight()/3+enemyOffSet2[i],Gdx.graphics.getWidth()/14,Gdx.graphics.getHeight()/9);
				batch.draw(enemy3,enemyX[i],Gdx.graphics.getHeight()/3+enemyOffSet3[i],Gdx.graphics.getWidth()/14,Gdx.graphics.getHeight()/9);
				batch.draw(enemy4,enemyX[i],Gdx.graphics.getHeight()/3+enemyOffSet4[i],Gdx.graphics.getWidth()/14,Gdx.graphics.getHeight()/9);

				enemycircles[i]=new Circle(enemyX[i] + Gdx.graphics.getWidth()/20,  Gdx.graphics.getHeight()/3 + enemyOffSet[i] + Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/40);
				enemycircles2[i]=new Circle(enemyX[i] + Gdx.graphics.getWidth()/20,  Gdx.graphics.getHeight()/3 + enemyOffSet2[i] + Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/40);
				enemycircles3[i]=new Circle(enemyX[i] + Gdx.graphics.getWidth()/20,  Gdx.graphics.getHeight()/3 + enemyOffSet3[i] + Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/40);
				enemycircles4[i]=new Circle(enemyX[i] + Gdx.graphics.getWidth()/20,  Gdx.graphics.getHeight()/3 + enemyOffSet4[i] + Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/40);




			}






			if(birdy>0)
			{
			velocity= velocity+gravity;
			birdy= birdy-velocity;
		}
		else{
				gameState=2;
			}

		}
		else if(gameState==0){
			if(Gdx.input.justTouched()){
				gameState=1;
			}
		}
		else if(gameState==2)
		{
			if(Gdx.input.justTouched()){
				gameState=1;

				birdy=Gdx.graphics.getHeight()/3;

				for(int i=0; i<numberOfEnemies; i++)
				{
					enemyOffSet[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight());
					enemyOffSet2[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight());
					enemyOffSet3[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight());
					enemyOffSet4[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight());

					enemyX[i]=Gdx.graphics.getWidth()-enemy1.getWidth()/2+i*distance;


					enemycircles[i]= new Circle();
					enemycircles2[i]= new Circle();
					enemycircles3[i]= new Circle();
					enemycircles4[i]= new Circle();

				}
				velocity=0;
				scoredEnemy=0;
				score=0;
			}

		}


		batch.draw(bird,birdx,birdy,Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
		font.draw(batch, String.valueOf(score),100,200);

		batch.end();


		birdcircle.set(birdx+Gdx.graphics.getWidth()/30,birdy+ Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.BLACK);
		//shapeRenderer.circle(birdcircle.x,birdcircle.y,birdcircle.radius);

		for(int i=0;i<numberOfEnemies;i++)
		{
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth()/20,  Gdx.graphics.getHeight()/3 + enemyOffSet[i] + Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/30);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth()/20,  Gdx.graphics.getHeight()/3 + enemyOffSet2[i] + Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/30);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth()/20,  Gdx.graphics.getHeight()/3 + enemyOffSet3[i] + Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/30);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth()/20,  Gdx.graphics.getHeight()/3 + enemyOffSet4[i] + Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/30);

			if(Intersector.overlaps(birdcircle,enemycircles[i])|| Intersector.overlaps(birdcircle,enemycircles2[i]) || Intersector.overlaps(birdcircle,enemycircles3[i])|| Intersector.overlaps(birdcircle, enemycircles4[i]))//çarpışma kontrolü
			{//çarpışma kontrolü
				gameState=2;//oyun bitimi
			}
		}


		//shapeRenderer.end();
	}
	
	@Override
	public void dispose () {

	}
}
