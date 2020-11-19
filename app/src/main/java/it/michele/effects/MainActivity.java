package it.michele.effects;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayDeque;

public class MainActivity extends AppCompatActivity {

    private ArrayDeque<Drawable> drawables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AssetManager assetManager = getAssets();
        String[] files;

        try {
            files = assetManager.list("immagini");
        } catch (IOException e) {
            e.printStackTrace();
            files = new String[0];
        }
        drawables = new ArrayDeque<>();

        for(String file : files){
            try{
                Drawable drawable = Drawable.createFromStream(
                        assetManager.open("immagini/"+file), null);
                drawables.add(drawable);
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        ImageView immagine = findViewById(R.id.immagine);

        immagine.setOnClickListener(view -> {
            immagine.animate()
                    .alpha(0)
                    .setDuration(500)
                    .withEndAction(() -> {
                        immagine.setImageDrawable(getNext());
                        immagine.animate()
                                .alpha(1)
                                .setDuration(500).start();
                    }).start();
        });

        /*
        immagine.setOnClickListener(view -> {
            Integer id = (Integer) immagine.getTag();
            if(id == null) id = -1;
            switch (id){
                case R.drawable.first:
                    immagine.setImageResource(R.drawable.second);
                    immagine.setTag(R.drawable.second);
                    break;
                case R.drawable.second:
                    immagine.setImageResource(R.drawable.third);
                    immagine.setTag(R.drawable.third);
                    break;
                case R.drawable.third:
                case -1:
                    immagine.setImageResource(R.drawable.first);
                    immagine.setTag(R.drawable.first);
                    break;
            }

            immagine.animate()
                    .alpha(0)
                    .setDuration(500)
                    .withEndAction(() -> {
                        immagine.animate()
                                .alpha(1)
                                .setDuration(500).start();
                    }).start();
        });*/
    }

    private Drawable getNext(){
        Drawable value = null;
        if(!drawables.isEmpty()){
            value = drawables.getFirst();
            drawables.removeFirst();
            drawables.addLast(value);
        }
        return value;
    }
}