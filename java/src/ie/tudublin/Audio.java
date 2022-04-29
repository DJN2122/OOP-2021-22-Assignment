package ie.tudublin;

// Importing all relevant packages
import java.util.ArrayList;
import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

// Audio class
public class Audio extends PApplet
{
    Minim minim;
    AudioPlayer ap;
    AudioInput ai;
    AudioBuffer ab;

    float[] lerpedBuffer;
    float y = 0;
    float smoothedY = 0;
    float smoothedAmplitude = 0;

    String[] names = {"Case 1", "Case 2", "Case 3", "Case 4", "Case 5", "Case 6", "Case 7", "Case 8", "Case 9"};

    public void settings()
	{
		fullScreen(P3D, SPAN);
	}

	public void setup() 
	{
        minim = new Minim(this);
        ap = minim.loadFile("noMoney.mp3", 1024);
        ap.play();
        ab = ap.mix;
        colorMode(HSB);

        y = height / 2;
        smoothedY = y;

        lerpedBuffer = new float[width];

		colorMode(HSB);
    }

    int mode = 0;

    public void keyPressed() {
		if (key >= '0' && key <= '9') {
			mode = key - '0';
		}
        if (keyCode == ESC) 
        {
            exit(); // exits out of program when "ESC" is pressed
        }
		println(mode);
	}

    public void mouseClicked()
    {
        if(mouseX >= 560 && mouseX <= 630 && mouseY >= 265&& mouseY <= 280 )
        {
            mode = 1;
        }
        
        System.out.println(mouseX + "+" + mouseY);
    }

    float off = 0;

    public void draw()
	{	
        float average = 0;
        float sum = 0;
        off += 1;
        // Calculate sum and average of the samples
        // Also lerp each element of buffer;
        for(int i = 0 ; i < ab.size() ; i ++)
        {
            sum += abs(ab.get(i));
            lerpedBuffer[i] = lerp(lerpedBuffer[i], ab.get(i), 0.05f);
        }
        average= sum / (float) ab.size();

        smoothedAmplitude = lerp(smoothedAmplitude, average, 0.1f);

        switch (mode) {
			case 0:
            {
                background(0);
                strokeWeight(2);

                for(int i = width/4  ; i < ab.size() ; i ++)
                {
                    float c = map(i, 0, ab.size(), 0, 255);
                    stroke(c, 255, 255);
                    float f = lerpedBuffer[i] * height/2 * 4.0f;
                    line(i + height/5, height/2 + f/5, i + height/5, height/2 - f/5);     
                }

                strokeWeight(20);
                stroke(255);
                noFill();
                rectMode(CENTER);
                square(width/2, height/2, height/2);

                int j = height/2 - height/4;

                for(int i = 0 ; i < names.length; i ++)
                {
                    fill(255);
                    textAlign(CENTER, CENTER);
                    textSize(25);
                    text(names[i], width/2 - height/3, j);
                    j = j + height /17;
                }
                break;
            }
            case 1:
            {
                background(0);
                stroke(255);
                circle(width/2, height/2, 200);

                strokeWeight(20);
                stroke(255);
                noFill();
                rectMode(CENTER);
                square(width/2, height/2, height/2);

                int j = height/2 - height/4;

                for(int i = 0 ; i < names.length; i ++)
                {
                    fill(255);
                    textAlign(CENTER, CENTER);
                    textSize(25);
                    text(names[i], width/2 - height/3, j);
                    j = j + height /17;
                }
            }
	    }
    }
}