package robn.light;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Light extends Activity {
    public boolean flashing;
    public static Camera cam = null;
    ImageView flash;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);
        flashing = false;
        flash = (ImageView) findViewById(R.id.flashLight);
        flash.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v){
                flash(v);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //().inflate(R.menu.main, menu);
        return true;
    }

    public void flash(View v) {
        if (flashing == false) {
            try {
                v.setBackgroundResource(R.drawable.green);
                if (getPackageManager().hasSystemFeature(
                        PackageManager.FEATURE_CAMERA_FLASH)) {
                    cam = Camera.open();
                    Parameters p = cam.getParameters();
                    p.setFlashMode(Parameters.FLASH_MODE_TORCH);
                    cam.setParameters(p);
                    cam.startPreview();
                    flashing = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getBaseContext(), "Exception flashLightOn()",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            try {
                v.setBackgroundResource(R.drawable.blue);
                if (getPackageManager().hasSystemFeature(
                        PackageManager.FEATURE_CAMERA_FLASH)) {
                    cam.stopPreview();
                    cam.release();
                    cam = null;
                    flashing = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getBaseContext(), "Exception flashLightOff",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
