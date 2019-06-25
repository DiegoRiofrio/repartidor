package es.upm.miw.repartidor;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import es.upm.miw.repartidor.estado.Estado;
import es.upm.miw.repartidor.objetos.FirebaseReferences;
import es.upm.miw.repartidor.objetos.Pedido;

public class RepartosActivity extends AppCompatActivity {

        private List<Pedido> listPedido = new ArrayList<Pedido>();
        ArrayAdapter<Pedido>arrayAdapterPedido;

        EditText numA, nomC, dirP;
        TextView estP, estR, estE, fechReg, fechRep, fechEnt;
        ListView listV_pedidos;

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        Pedido pedidoSelected;

    final DatabaseReference repartidorRef = database.getReference(FirebaseReferences.REPARTIDOR_REFERENCE);

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_repartos);

            numA = findViewById(R.id.txt_NumArticulos);
            nomC = findViewById(R.id.txt_Cliente);
            dirP = findViewById(R.id.txt_Direccion);
            estP = (TextView) findViewById(R.id.txt_Estado);
            estR = (TextView) findViewById(R.id.txt_EstadoReparto);
            estE = (TextView) findViewById(R.id.txt_EstadoEntregado);
            fechReg = (TextView) findViewById(R.id.txt_FechReg);
            fechRep = (TextView) findViewById(R.id.txt_FechReparto);
            fechEnt = (TextView) findViewById(R.id.txt_FechEnt);


            listV_pedidos = findViewById(R.id.listaPedidos);

            listaDatos();

            listV_pedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    pedidoSelected = (Pedido) parent.getItemAtPosition(position);
                    numA.setText(String.valueOf(pedidoSelected.getArticulos()));
                    nomC.setText(pedidoSelected.getCliente());
                    dirP.setText(pedidoSelected.getDireccion());
                    estP.setText(String.valueOf(pedidoSelected.getEstado()));
                    estR.setText(String.valueOf(pedidoSelected.getEncamino()));
                    estE.setText(String.valueOf(pedidoSelected.getEntregado()));
                    fechReg.setText(String.valueOf(pedidoSelected.getFecha_registro()));
                    fechRep.setText(String.valueOf(pedidoSelected.getFecha_encamino()));
                    fechEnt.setText(String.valueOf(pedidoSelected.getFecha_entrega()));

                }
            });

        }

    private void listaDatos() {
            repartidorRef.child(FirebaseReferences.REPARTO_REFERENCE).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listPedido.clear();
                    for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                        Pedido p = objSnapshot.getValue(Pedido.class);
                        listPedido.add(p);

                        arrayAdapterPedido = new ArrayAdapter<Pedido>(RepartosActivity.this, android.R.layout.simple_list_item_1, listPedido);
                        listV_pedidos.setAdapter(arrayAdapterPedido);


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
    }

    public boolean onCreateOptionsMenu(Menu menu){
            super.onCreateOptionsMenu(menu);
            getMenuInflater().inflate(R.menu.menu_repartos,menu);
            return  true;
        }


    public boolean onOptionsItemSelected(MenuItem item) {

            String cliente = nomC.getText().toString();
            String direccion = dirP.getText().toString();
            Estado estadoI = Estado.PROCESADO;
            Estado estadoR = Estado.EN_CAMINO;
            Estado estadoE = Estado.ENTREGADO;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
            String date = sdf.format(new Date());
        switch (item.getItemId()) {
            case R.id.icon_add:{
                if(((numA.getText().toString().isEmpty()) ||cliente.isEmpty()||direccion.equals(""))){
                    validacion();
                }
                else {
                    Pedido p = new Pedido();
                    p.setReferencia(UUID.randomUUID().toString());
                    p.setArticulos(Integer.valueOf(numA.getText().toString()));
                    p.setCliente(cliente);
                    p.setDireccion(direccion);
                    p.setEstado(estadoI);
                    p.setEncamino(estadoR);
                    p.setEntregado(estadoE);
                    p.setFecha_registro(date);
                    p.setFecha_encamino(fechRep.getText().toString().trim());
                    p.setFecha_entrega(fechEnt.getText().toString().trim());
                    repartidorRef.child(FirebaseReferences.REPARTO_REFERENCE).child(p.getReferencia()).setValue(p);
                    Toast.makeText(this, "Agregado", Toast.LENGTH_LONG).show();
                    limpiarCajas();
                }

                break;
            }
            case R.id.icon_modify:{
                Pedido p = new Pedido();
                p.setReferencia(pedidoSelected.getReferencia());
                p.setArticulos(Integer.parseInt(numA.getText().toString().trim()));
                p.setCliente(nomC.getText().toString().trim());
                p.setDireccion(dirP.getText().toString().trim());
                p.setEstado(Estado.valueOf(estP.getText().toString().trim()));
                p.setEncamino(estadoR);
                p.setEntregado(estadoE);
                p.setFecha_registro(fechReg.getText().toString().trim());
                p.setFecha_encamino(fechRep.getText().toString().trim());
                p.setFecha_entrega(fechEnt.getText().toString().trim());
                repartidorRef.child(FirebaseReferences.REPARTO_REFERENCE).child(p.getReferencia()).setValue(p);
                Toast.makeText(this, "Modificado", Toast.LENGTH_LONG).show();
                limpiarCajas();
                break;
            }
            case R.id.icon_del:{
                Pedido p = new Pedido();
                p.setReferencia(pedidoSelected.getReferencia());
                repartidorRef.child(FirebaseReferences.REPARTO_REFERENCE).child(p.getReferencia()).removeValue();
                limpiarCajas();
                break;
            }

            case R.id.icon_enruta:{
                Pedido p = new Pedido();
                p.setReferencia(pedidoSelected.getReferencia());
                p.setArticulos(Integer.parseInt(numA.getText().toString().trim()));
                p.setCliente(nomC.getText().toString().trim());
                p.setDireccion(dirP.getText().toString().trim());
                p.setEstado(Estado.valueOf(estP.getText().toString().trim()));
                p.setEncamino(estadoR);
                p.setEntregado(estadoE);
                p.setFecha_registro(fechReg.getText().toString().trim());
                p.setFecha_encamino(date);
                p.setFecha_entrega(fechEnt.getText().toString().trim());
                repartidorRef.child(FirebaseReferences.REPARTO_REFERENCE).child(p.getReferencia()).setValue(p);
                Toast.makeText(this, "En reparto", Toast.LENGTH_LONG).show();
                limpiarCajas();
                break;
            }
            case R.id.icon_entrega:{
                Pedido p = new Pedido();
                p.setReferencia(pedidoSelected.getReferencia());
                p.setArticulos(Integer.parseInt(numA.getText().toString().trim()));
                p.setCliente(nomC.getText().toString().trim());
                p.setDireccion(dirP.getText().toString().trim());
                p.setEstado(Estado.valueOf(estP.getText().toString().trim()));
                p.setEncamino(estadoR);
                p.setEntregado(estadoE);
                p.setFecha_registro(fechReg.getText().toString().trim());
                p.setFecha_encamino(fechRep.getText().toString().trim());
                p.setFecha_entrega(date);
                repartidorRef.child(FirebaseReferences.REPARTO_REFERENCE).child(p.getReferencia()).setValue(p);
                Toast.makeText(this, "Entregado", Toast.LENGTH_LONG).show();
                limpiarCajas();
                break;
            }
            case R.id.icon_exit:{
                Toast.makeText(this, "Adios ", Toast.LENGTH_LONG).show();
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
            estR.setText("");
            estE.setText("");
            fechReg.setText("");
            fechRep.setText("");
            fechEnt.setText("");

    }

    private void validacion() {
        Integer articulos = Integer.valueOf(numA.getText().toString());
        String cliente = nomC.getText().toString();
        String direccion = dirP.getText().toString();
        //String estado = estP.getText().toString();


        if (articulos.equals("")){
            numA.setError("Requiered");
        }
        else if (cliente.equals("")){
                nomC.setError("Requiered");
        }
        else if (direccion.equals("")){
            dirP.setError("Requiered");
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

