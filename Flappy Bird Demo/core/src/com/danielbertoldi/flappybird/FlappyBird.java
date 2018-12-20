package com.danielbertoldi.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
    SpriteBatch batch;
    Texture background;
    Texture[] birds;
    Texture pipeUp;
    Texture pipeDown;
    Circle birdCircle;
    Rectangle[] topPipeRectangle;
    Rectangle[] bottomPipeRectangle;
    BitmapFont font;
    Texture gameOver;
    Random numberGenerator;
    int flapState = 0;
    float birdY = 0;
    float velocity = 0;
    int gameState = 0;
    float gravity = 2;
    float gap = 400;
    float maxTubeOffset;
    float tubeVelocity = 4;
    int numberOfTubes = 4;
    float[] tubesX = new float[numberOfTubes];
    float[] tubeOffset = new float[numberOfTubes];
    float distanceBetweenTubes;
    int score = 0;
    int scoringTube = 0;

    @Override
    public void create () {
        birdCircle = new Circle();
        topPipeRectangle = new Rectangle[numberOfTubes];
        bottomPipeRectangle = new Rectangle[numberOfTubes];
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(15);

        /* Add sprites as batch/ sprite = image, char, bg */
        /* SpriteBatch is used to manage and display the animation of said sprites */
        batch = new SpriteBatch();
        background = new Texture("bg.png");

        birds = new Texture[2];
        birds[0] = new Texture("bird.png");
        birds[1] = new Texture("bird2.png");

        /* Holds the current bird animation for 0.15s */
        new com.badlogic.gdx.utils.Timer().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                if(gameState != 2) {
                    if (flapState == 0) {
                        flapState = 1;
                    } else {
                        flapState = 0;
                    }
                }
            }
        }, 0f,0.15f);

        pipeUp = new Texture("toptube.png");
        pipeDown = new Texture("bottomtube.png");

        maxTubeOffset = Gdx.graphics.getHeight()/2 - gap/2 - 100;
        numberGenerator = new Random();
        distanceBetweenTubes = Gdx.graphics.getWidth() * 3 / 4;

        gameOver = new Texture("gameover.png");

        startGame();
    }

    @Override
    public void render () {

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        /* game is active */
        if(gameState == 1) {
            /* for each touch, make the bird go up */
            if(Gdx.input.justTouched()) {
                velocity = -30;
                batch.draw();
            }

            if(tubesX[scoringTube] < Gdx.graphics.getWidth()/2){
                score++;

                if (scoringTube < numberOfTubes - 1)
                    scoringTube++;
                else
                    scoringTube = 0;
            }

            /* moves the pipes */
            for(int i = 0; i < numberOfTubes; i++) {
                /* if it's out of the screen */
                if(tubesX[i] < -pipeUp.getWidth()) {
                    tubesX[i] += numberOfTubes * distanceBetweenTubes;
                    tubeOffset[i] = (numberGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
                } else {
                    /* moves the pipes to the left */
                    tubesX[i] -= tubeVelocity;
                }

                /* bring in the pipes */
                batch.draw(pipeUp, tubesX[i], Gdx.graphics.getHeight()/2 + gap/2 + tubeOffset[i]);
                batch.draw(pipeDown, tubesX[i], Gdx.graphics.getHeight()/2 - gap/2 - pipeDown.getHeight() + tubeOffset[i]);

                topPipeRectangle[i] = new Rectangle(tubesX[i], Gdx.graphics.getHeight()/2 + gap/2 + tubeOffset[i], pipeUp.getWidth(), pipeUp.getHeight());
                bottomPipeRectangle[i] = new Rectangle(tubesX[i], Gdx.graphics.getHeight()/2 - gap/2 - pipeDown.getHeight() + tubeOffset[i], pipeDown.getWidth(), pipeDown.getHeight());
            }

            /* just so the bird doesn't fall off the screen */
            if(birdY > 0) {
                velocity = velocity + gravity;
                birdY -= velocity;
            } else {
                gameState = 2;
            }
        /* game is not ready */
        } else if(gameState == 0){
            if(Gdx.input.justTouched()){
                gameState = 1;
                velocity = -20;
            }
        } else if(gameState == 2){
            batch.draw(gameOver, Gdx.graphics.getWidth()/2 - gameOver.getWidth()/2, Gdx.graphics.getHeight()/2 - gameOver.getHeight()/2);

            /* waits for tap */
            if(Gdx.input.justTouched()){
                gameState = 1;
                score = 0;
                scoringTube = 0;
                velocity = 0;
                startGame();
            }
        }

        batch.draw(birds[flapState], Gdx.graphics.getWidth()/2 - birds[flapState].getWidth()/2, birdY);
        font.draw(batch, String.valueOf(score), 100, 300);

        birdCircle.set(Gdx.graphics.getWidth()/2, birdY + birds[flapState].getHeight()/2, birds[flapState].getHeight()/2);

        for(int i = 0; i < numberOfTubes; i++) {
            if(Intersector.overlaps(birdCircle, topPipeRectangle[i]) || Intersector.overlaps(birdCircle, bottomPipeRectangle[i])){
                gameState = 2;
            }
        }

        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        background.dispose();
        birds[0].dispose();
        birds[1].dispose();
    }

    public void startGame(){
        birdY = Gdx.graphics.getHeight()/2 - birds[0].getHeight()/2;

        /* set up tubes */
        for(int i = 0; i < numberOfTubes; i++){
            /* we are going to set each tube x coordinate and offset */
            tubeOffset[i] = (numberGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
            tubesX[i] = Gdx.graphics.getWidth()/2 - pipeUp.getWidth()/2 + Gdx.graphics.getWidth() + i * distanceBetweenTubes;

            topPipeRectangle[i] = new Rectangle();
            bottomPipeRectangle[i] = new Rectangle();
        }

    }
}
