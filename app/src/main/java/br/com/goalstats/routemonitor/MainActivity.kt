package br.com.goalstats.routemonitor
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
class MainActivity : Activity() {
 override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  val info=TextView(this).apply {
   text="""Monitor de Rotas — V1

Permitidos:
• Aeroclube
• Bessa
• Jardim Oceania
• Manaíra

TODAS as entregas precisam estar nos bairros permitidos.
A coleta não entra no filtro.
Após “Gerar Rota”, novos cliques ficam bloqueados por 15 s.

Mantenha “ROTAS DISPONÍVEIS” aberta."""
   textSize=17f; setPadding(32,48,32,32)
  }
  val button=Button(this).apply { text="ABRIR CONFIGURAÇÕES DE ACESSIBILIDADE"; setOnClickListener { startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)) } }
  setContentView(LinearLayout(this).apply { orientation=LinearLayout.VERTICAL; addView(info); addView(button) })
 }
}
