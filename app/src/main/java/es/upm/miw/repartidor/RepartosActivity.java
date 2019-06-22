package es.upm.miw.repartidor;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

import es.upm.miw.repartidor.estado.Estado;
import es.upm.miw.repartidor.objetos.FirebaseReferences;
import es.upm.miw.repartidor.objetos.Pedido;

public class RepartosActivity extends AppCompatActivity {

        Button buttonReparto;
        EditText numA, nomC, dirP, estP;
        ListView ListV_pedidos;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference repartidorRef = database.getReference(FirebaseReferences.REPARTIDOR_REFERENCE);

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_repartos);

            buttonReparto = (Button) findViewById(R.id.boton_reparto);

            numA = findViewById(R.id.txt_NumArticulos);
            nomC = findViewById(R.id.txt_Cliente);
            dirP = findViewById(R.id.txt_Direccion);
            estP = findViewById(R.id.txt_Estado);

            ListV_pedidos = findViewById(R.id.listaPedidos);


            buttonReparto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Pedido reparto = new Pedido(5,"Pepe", "calle buena vista", Estado.PROCESADO);
                    repartidorRef.child(FirebaseReferences.REPARTO_REFERENCE).push().setValue(reparto);
                }
            });

        }

    public boolean onCreateOptionsMenu(Menu menu){
            super.onCreateOptionsMenu(menu);
            getMenuInflater().inflate(R.menu.menu_repartos,menu);
            return  true;
        }


    public boolean onOptionsItemSelected(MenuItem item) {
            Integer articulos = Integer.parseInt(numA.getText().toString());
            String cliente = nomC.getText().toString();
            String direccion = dirP.getText().toString();
            String estado = estP.getText().toString();

        switch (item.getItemId()) {
            case R.id.icon_add:{
                if(((articulos == null) ||cliente.equals("")||direccion.equals("")||estado.equals(""))){
                    validacion();

                }
                else {
                    Pedido p = new Pedido();
                    p.setReferencia(UUID.randomUUID().toString());
                    p.setArticulos(articulos);
                    p.setCliente(cliente);
                    p.setDireccion(direccion);
                    // repartidorRef.child(FirebaseReferences.REPARTO_REFERENCE).push().setValue(p);
                    repartidorRef.child(FirebaseReferences.REPARTO_REFERENCE).child(p.getReferencia()).setValue(p);
                    limpiarCajas();
                }

                break;
            }
            case R.id.icon_modify:{
                onBackPressed();
                break;
            }
            case R.id.icon_del:{
                exit();
                break;
            }
            default:break;
        }
        return true;
    }

    private void limpiarCajas() {
            numA.setText("");
            nomC.setText("");
            dirP.setText("");
            estP.setText("");
    }

    private void validacion() {
            String cliente = nomC.getText().toString();
        String direccion = dirP.getText().toString();
        String estado = estP.getText().toString();
        Integer articulos = Integer.valueOf(numA.getText().toString());

        if (articulos == null){
            numA.setError("Requiered");
        }
        else if (cliente.equals("")){
                nomC.setError("Requiered");
        }
        else if (direccion.equals("")){
            dirP.setError("Requiered");
        }
        else if (estado.equals("")){
            estP.setError("Requiered");
        }
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    private void exit() {
        signOut();
        this.finish();
    }


    }

