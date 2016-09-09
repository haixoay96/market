package app.android.thaihn.market.activity;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

import app.android.thaihn.market.Piece;
import app.android.thaihn.market.R;
import app.android.thaihn.market.adapter.ArrayAdapterRow;

public class CreatedActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<Piece> pieceArrayList;
    private ArrayList<Piece> pieceArrayListCache;
    private ArrayAdapterRow arrayAdapterRow;
    private Toolbar toolbar;
    private String search = "";
    public void initiView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView = (ListView) findViewById(R.id.created_list);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created);
        initiView();
        setSupportActionBar(toolbar);
        pieceArrayList = new ArrayList<Piece>();
        pieceArrayListCache = new ArrayList<Piece>();
        ArrayList<Piece> data = MainActivity.pieceArrayList;
        for(int i = 0 ; i<data.size();i++){
            if(getIntent().getStringExtra("user").equals(data.get(i).getUser())){
                pieceArrayListCache.add(data.get(i));
                if(data.get(i).getTitle().indexOf(search)>-1){
                    pieceArrayList.add(data.get(i));

                }
            }
        }
        arrayAdapterRow = new ArrayAdapterRow(this, pieceArrayList);
        listView.setAdapter(arrayAdapterRow);
        arrayAdapterRow.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CreatedActivity.this, DetailActivity.class);
                Piece.cache = pieceArrayList.get(position);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_created,menu);
        MenuItem menuItem = menu.findItem(R.id.searchview_created);
        final SearchView searchView = (SearchView)MenuItemCompat.getActionView(menuItem);
        searchView.setQueryHint("Tìm kiếm nhanh");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getChangeListPiece(newText);
                search = newText;
                arrayAdapterRow.notifyDataSetChanged();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    public void getChangeListPiece(String newText){
        pieceArrayList.removeAll(pieceArrayList);
        for(int i = 0; i<pieceArrayListCache.size();i++){
            if(pieceArrayListCache.get(i).getTitle().toLowerCase().indexOf(newText.toLowerCase())>-1){
                pieceArrayList.add(pieceArrayListCache.get(i));
            }
        }
    }
}
