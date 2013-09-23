package com.example.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.MyLocationOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

public class MyActivity extends Activity {

    private MapView mMapView;
    private MapController mMapController;
    ListView lvMain;
    ArrayList<OverlayItem> anotherOverlayItemArray;
    MyLocationOverlay myLocationOverlay = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        lvMain = (ListView) findViewById(R.id.listView);

        //Определение контроллеров карты
        mMapView = (MapView) findViewById(R.id.openmapview);
        mMapView.setTileSource(TileSourceFactory.MAPNIK);
        mMapController = mMapView.getController();
        //
        mMapController.setCenter(new GeoPoint((int) (45.046753 * 1E6), (int) (38.928509 * 1E6)));
        mMapController.setZoom(7);


        //Наполнение ListView
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.namess, R.layout.listview_item);
        lvMain.setAdapter(adapter);

        //Обработчик клика на элементе списка
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //  mMapController.setZoom(13);
                mMapController.animateTo(anotherOverlayItemArray.get((int) id).getPoint());
            }
        });

        //Кнопки
        Button buttonOut = (Button) findViewById(R.id.button);
        Button buttonIn = (Button) findViewById(R.id.button1);

        //Кнока "-"
        buttonOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMapController.zoomOut();
            }
        });

        //Кнопка "+"
        buttonIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMapController.zoomIn();
            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Карта
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //--- Create Another Overlay for multi marker
        anotherOverlayItemArray = new ArrayList<OverlayItem>();
        anotherOverlayItemArray.add(new OverlayItem(
                "Точка 1", "-", new GeoPoint((int) (45.21258 * 1E6), (int) (39.686294 * 1E6))));
        anotherOverlayItemArray.add(new OverlayItem(
                "Точка 2", "-", new GeoPoint((int) (45.198974 * 1E6), (int) (39.234724 * 1E6))));
        anotherOverlayItemArray.add(new OverlayItem(
                "Точка 3", "-", new GeoPoint((int) (45.116661 * 1E6), (int) (39.422951 * 1E6))));
        anotherOverlayItemArray.add(new OverlayItem(
                "Точка 4", "-", new GeoPoint((int) (45.046753 * 1E6), (int) (38.928509 * 1E6))));
        anotherOverlayItemArray.add(new OverlayItem(
                "Точка 5", "-", new GeoPoint((int) (45.039514 * 1E6), (int) (38.974287 * 1E6))));
        anotherOverlayItemArray.add(new OverlayItem(
                "Точка 6", "-", new GeoPoint((int) (45.018245 * 1E6), (int) (38.989102 * 1E6))));
        anotherOverlayItemArray.add(new OverlayItem(
                "Точка 7", "-", new GeoPoint((int) (45.026632 * 1E6), (int) (38.956529 * 1E6))));
        anotherOverlayItemArray.add(new OverlayItem(
                "Точка 8", "-", new GeoPoint(15 * 1E6, 19 * 1E6)));
        anotherOverlayItemArray.add(new OverlayItem(
                "Точка 9", "-", new GeoPoint(8 * 1E6, 6 * 1E6)));
        anotherOverlayItemArray.add(new OverlayItem(
                "Точка 10", "-", new GeoPoint(2 * 1E6, 1 * 1E6)));

        ItemizedIconOverlay<OverlayItem> anotherItemizedIconOverlay
                = new ItemizedIconOverlay<OverlayItem>(
                this, anotherOverlayItemArray, myOnItemGestureListener);
        mMapView.getOverlays().add(anotherItemizedIconOverlay);


        //Add MyLocationOverlay
        myLocationOverlay = new MyLocationOverlay(this, mMapView);
        mMapView.getOverlays().add(myLocationOverlay);
        mMapView.postInvalidate();


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Анимация                         Не окончена
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        final LinearLayout LL = (LinearLayout) findViewById(R.id.LL);
        final ListView lv = (ListView) findViewById(R.id.listView);

        final RelativeLayout rla = (RelativeLayout) findViewById(R.id.relativeLayout);
        //отдельно
        lvMain.setOnTouchListener(new View.OnTouchListener() {
            int mLastY = 0
                    ,
                    curY
                    ,
                    deltaY_ = 0;

            boolean locker = false;
            TextView textV = (TextView) findViewById(R.id.textView);

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (event.getRawY() < 300) {

                        LinearLayout.LayoutParams lvp = new LinearLayout.LayoutParams(440, 700);

                        lvp.setMargins(20, 200, 0, 0);
                        lv.setLayoutParams(lvp);
                        return false;


                    }
                    //final int X = (int) event.getRawX();
                    //final int Y = (int) event.getRawY();

                    // deltaY_ = Y -layoutParams.bottomMargin;


                    LinearLayout.LayoutParams lvp = new LinearLayout.LayoutParams(440, 700);

                    lvp.setMargins(20, (int) event.getRawY() - 100, 0, 0);
                    //    LL.setLayoutParams(lvp);
                    //no needs to setLayout again: view_.setLayoutParams(layoutParams);

                    //requestLayout is required
                    lv.setLayoutParams(lvp);


                    return true;
                }
                return false;
            }
        });


    }


    ItemizedIconOverlay.OnItemGestureListener<OverlayItem> myOnItemGestureListener
            = new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {

        @Override
        public boolean onItemLongPress(int arg0, OverlayItem arg1) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean onItemSingleTapUp(int index, OverlayItem item) {
            Toast.makeText(MyActivity.this,
                    item.mDescription + "\n"
                            + item.mTitle + "\n"
                            + item.mGeoPoint.getLatitudeE6() + " : " + item.mGeoPoint.getLongitudeE6(),
                    Toast.LENGTH_LONG).show();
            return true;
        }

    };

}

