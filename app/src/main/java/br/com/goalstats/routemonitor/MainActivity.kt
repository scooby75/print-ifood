package br.com.goalstats.routemonitor

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val disclosure = TextView(this).apply {
            text = """Monitor de Rotas — V1

Uso da Acessibilidade

Este app usa o Serviço de Acessibilidade para ler, somente enquanto o serviço estiver ativado, o conteúdo exibido na tela “Rotas Disponíveis” e identificar os bairros das entregas.

Regra automática definida por você:
• bairros permitidos: Aeroclube, Bessa, Jardim Oceania e Manaíra;
• todas as entregas do card precisam estar nesses bairros;
• a coleta é ignorada;
• quando a regra for satisfeita, o app aciona “Gerar Rota”;
• a tela seguinte permanece manual;
• há bloqueio de 15 segundos contra clique duplicado.

O app não precisa dessa permissão para outros fins e esta versão não envia o conteúdo lido para servidores."""
            textSize = 16f
            setPadding(32, 40, 32, 20)
        }

        val consent = CheckBox(this).apply {
            text = "Li e concordo com o uso da Acessibilidade descrito acima."
            setPadding(24, 8, 24, 8)
        }

        val button = Button(this).apply {
            text = "ATIVAR SERVIÇO DE ACESSIBILIDADE"
            isEnabled = false
            setOnClickListener {
                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            }
        }

        consent.setOnCheckedChangeListener { _, checked -> button.isEnabled = checked }

        setContentView(LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            addView(disclosure)
            addView(consent)
            addView(button)
        })
    }
}
