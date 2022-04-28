package ie.tudublin;

// import all relevant packages
import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;

// audio class
public class Audio extends PApplet
{
    Minim minim;
    AudioPlayer ap;
    AudioInput ai;
    AudioBuffer ab;

    int mode = 0;

    float[] lerpedBuffer;
    float y = 0;
    float smoothedY = 0;
    float smoothedAmplitude = 0;

    public void keyPressed() 
    {
		if (key >= '0' && key <= '9')
        {
			mode = key - '0';
		}

		if (keyCode == ' ') 
        {
            if (ap.isPlaying()) 
            {
                ap.pause();
            } 

            else 
            {
                ap.rewind();
                ap.play();
            }
        }
        
        if (keyCode == ESC) 
        {
            exit(); // exits out of program when "ESC" is pressed
        }
	}

    // settings method
    public void settings()
    {
        // size(1024,1000);
        fullScreen(P3D, SPAN); //P3D
    }

    // setup method
    public void setup()
    {
        minim = new Minim(this);
        // Uncomment this to use the microphone
        // ai = minim.getLineIn(Minim.MONO, width, 44100, 16);
        // ab = ai.mix; 
        ap = minim.loadFile("noMoney.mp3", 1024);
        ap.play();
        ab = ap.mix;
        colorMode(HSB);

        y = height / 2;
        smoothedY = y;

        lerpedBuffer = new float[width];
    }

    float off = 0;

    // draw method
    public void draw()
    {
        float halfH = height / 2;
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
        
        float cx = width / 2;
        float cy = height / 2;

        switch (mode) 
        {
            // case when "0" is pressed
			case 0:
            {
                background(0);
                for(int i = 0 ; i < ab.size() ; i ++)
                {
                    //float c = map(ab.get(i), -1, 1, 0, 255);
                    float c = map(i, 0, ab.size(), 0, 255);
                    stroke(c, 255, 255);
                    float f = lerpedBuffer[i] * halfH * 4.0f;
                    line(i, halfH + f, i, halfH - f);                    
                }
                break;
            }

            // case when "1" is pressed
            case 1:
            {
                background(0);
                for(int i = 0 ; i < ab.size() ; i ++)
                {
                    //float c = map(ab.get(i), -1, 1, 0, 255);
                    float c = map(i, 0, ab.size(), 0, 255);
                    stroke(c, 255, 255);
                    float f = lerpedBuffer[i] * halfH * 4.0f;
                    line(i, halfH + f, halfH - f, i);                    
                }
                break;
            }

            // case when "2" is pressed
            case 2:
            {
                background(0, 0, 0, 10);
                float c = map(smoothedAmplitude, 0, 0.5f, 0, 255);
                stroke(c, 255, 255);	
                float radius = map(smoothedAmplitude, 0, 0.1f, 50, 300);		
                int points = (int)map(mouseX, 0, 255, 3, 10);
                int sides = points * 2;
                float px = cx;
                float py = cy - radius; 

                for(int i = 0 ; i <= sides ; i ++)
                {
                    float r = (i % 2 == 0) ? radius : radius / 2; 
                    // float r = radius;
                    float theta = map(i, 0, sides, 0, TWO_PI);
                    float x = cx + sin(theta) * r;
                    float y = cy - cos(theta) * r;
                            
                    //circle(x, y, 20);
                    line(px, py, x, y);
                    px = x;
                    py = y;
                }
                break;
            }

            // case when "3" is pressed
            case 3:
            {
                background(0);
                strokeWeight(2);
                noFill();

                float r = map(smoothedAmplitude, 0, 0.5f, 100, 2000);
                float c = map(smoothedAmplitude, 0, 0.5f, 0, 255);
                stroke(c, 255, 255);
                circle(cx, cy, r);

                break;
            }

            // case when "4" is pressed
            case 4:
            {
                background(0);
                strokeWeight(2);
                
                for(int i = 0 ; i < ab.size() ; i +=10)
                {
                    //float c = map(ab.get(i), -1, 1, 0, 255);
                    float cc = map(i, 0, ab.size(), 0, 255);
                    stroke(cc, 255, 255);
                    float f = map(smoothedAmplitude, 0, 0.5f, 0, 255);
                    //line(i, halfH + f, i, halfH - f);
                    fill(cc);
                    circle(i, halfH + f, 5);                    
                    circle(i, halfH - f, 5);                    
                }
                break;
            }

            // case when "5" is pressed
            case 5:
            {
                background(0);
                strokeWeight(2);

                double i=0;float k=0;
                float x;
                float y;
                x=(float)(k*Math.cos(i)+960);
                y=(float)(k*Math.sin(i)+500);
                println(i);
                point(x,y);
                i=i+(Math.PI)/(180);
                k=k+(40f/360f);
                
                break;
            }

            // case when "6" is pressed
            case 6:
            {
                noStroke();
                fill(0, 5);
                rect(0,0,width,height);
                pushMatrix();
                translate(width/2, height/2);
                rotate(radians(frameCount % 360 * 2));
                //float c = map(i, 0, ab.size(), 0, 255);
                for(int j = 0; j < 360; j++) 
                {
                    
                    if(ap.mix.get(j)*200 > 50) 
                    {
                        stroke(20,255,255);
                    }

                    else 
                    {
                        stroke(100,255,255);
                    }
                    
                    line(cos(j)*50, sin(j)*50, cos(j)*abs(ap.left.get(j))*200 + cos(j)*50, sin(j)*abs(ap.right.get(j))*200 + sin(j)*50);
                }

                for(int k = 360; k > 0; k--) 
                {
                     
                    if(ap.mix.get(k)*200 > 25) 
                    {
                        stroke(150,255,255);
                    }  

                    else 
                    {
                        stroke(200,255,255);
                    }
                    
                    line(cos(k)*50, sin(k)*50, cos(k)*abs(ap.right.get(k))*200 + cos(k)*50, sin(k)*abs(ap.left.get(k))*200 + sin(k)*50);   
                }
                
                popMatrix();
                break;
            }
               
        // case when "7" is pressed
        case 7:
        {
            background(0);
            strokeWeight(2);
                
            for(int i = 0 ; i < ab.size() ; i += 10)
            {
                float f = map(smoothedAmplitude, 0, 0.5f, 0, 750);
                float j = lerpedBuffer[i] * halfH / 4.0f;
                float cc = map(smoothedAmplitude, 0, 0.5f, 0, 255);
                stroke(cc, 255, 255);
                noFill();
                rectMode(CENTER);
                float sqSize = f;
                float halfSize = f/2;

                square(cx, cy, sqSize + j);
                line(cx + halfSize, cy + halfSize, width, height + j * 2);
                line(cx + halfSize, cy - halfSize, width, 0 - j * 2);
                line(cx - halfSize, cy - halfSize, 0, 0 - j * 2);
                line(cx - halfSize, cy + halfSize, 0, height + j * 2);
                // line(i, halfH + f, i, halfH - f); 
        
            }
                break;
        }
        case 8:
        {
            background(0);
            strokeWeight(2);
                
            for(int i = 0 ; i < ab.size() ; i += 10)
            {
                float f = map(smoothedAmplitude, 0, 0.5f, 0, 750);
                float j = lerpedBuffer[i] * halfH / 4.0f;
                float cc = map(smoothedAmplitude, 0, 0.5f, 0, 255);
                int msec = millis();
                println(msec);
                stroke(cc, 255, 255);
                noFill();
                float sqSize = f;
                float halfSize = f/2;

                float p = random(-1024, 1024);
                // stroke(255);
                // square(cx + p, cy + p, f*5);
                // square(cx - p, cy + p, f*6);

                background(0);
                rectMode(CENTER);
                // fill(255);
                // circle(cx, cy, halfH);
                // square(p * 2, cy, f);
                // circle(cx, cy, f);
                square(cx, cy, sqSize + j);
                line(cx + halfSize, cy + halfSize, width, height + f / 2);
                line(cx + halfSize, cy - halfSize, width, 0 - f / 2);
                line(cx - halfSize, cy - halfSize, 0, 0 - f / 2);
                line(cx - halfSize, cy + halfSize, 0, height + f / 2);

                    if (msec > 55000)
                    {
                        background(0);
                        square(cx, cy, sqSize + j);
                    }

                    if (msec > 62500)
                    {
                        float random = random(0, 255);
                        background(0);
                        stroke(random,255,255);
                        square(cx + p, cy + p, sqSize/2 + j);
                        square(cx - p, cy + p, sqSize/2 + j);
                        square(cx + p /2, cy + p /2, sqSize/2 + j);
                        square(cx - p /2, cy + p /2, sqSize/2 + j);
                        square(cx - p /2, cy - p /2, sqSize/2 + j);
                        square(cx + p /2, cy - p /2, sqSize/2 + j);

                        
                        break;
                    }
                    

                    // if (f < 200)
                    // {
                    //     background(0);
                    //     strokeWeight(4);
                    //     circle(cx + p, cy + p, f/2);
                    //     circle(cx - p, cy + p, f/2);
                        
                    //     break;
                    // }
                
            }
                break;
        }
        case 9:
        {
            // background(colour);

            for(int i = 0 ; i < ab.size() ; i += 10)
            {
                int colour = (int) map(mouseX, 0, 600, 0, 255);
                float f = map(smoothedAmplitude, 0, 0.5f, 0, 750);
                
                stroke(colour,colour,colour);
                noFill();
                circle(mouseX/2, cy, f);
            }

        }

    }

}
}