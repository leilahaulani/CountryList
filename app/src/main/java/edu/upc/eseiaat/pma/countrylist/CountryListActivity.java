package edu.upc.eseiaat.pma.countrylist;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class CountryListActivity extends AppCompatActivity {

    private ArrayList<String> country_list;
    private ArrayAdapter<String> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_list);

        String [] countries = getResources().getStringArray(R.array.countries);
        //Pasamos de una tabla a una lista.
        country_list = new ArrayList<>(Arrays.asList(countries));

        //Hago referencia al list view del layout.
        ListView list = (ListView) findViewById(R.id.conutry_list);

        //Todos los list views tienen un adaptador para que cuando desaparezca un item aprezca otro.
        adaptador= new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, country_list);
        list.setAdapter(adaptador);

        //Seleciona un país y te lo muesta en el Toast
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
                Toast.makeText(
                        CountryListActivity.this,
                        String.format("Has escrito: '%s' ", country_list.get(position)),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        //Nos permite eliminar un país.
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                //Preguntamos si está seguro de elimnar la ciudad.
                AlertDialog.Builder builder = new AlertDialog.Builder(CountryListActivity.this);
                builder.setTitle(R.string.confirmar);
                String msg = getResources().getString(R.string.eliminar_pais);
                builder.setMessage(msg + " " + country_list.get(position) + "?");
                builder.setPositiveButton(R.string.eliminar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        country_list.remove(position);
                        adaptador.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, null);
                builder.create().show();

                //Tengo inidcar que si hay el long no tiene que funcionar el otro. Ponemos un true.
                return true;
            }
        });

    }
}
