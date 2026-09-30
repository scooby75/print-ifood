package br.com.goalstats.routemonitor
import android.accessibilityservice.AccessibilityService
import android.os.SystemClock
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import java.text.Normalizer
import java.util.Locale
class RouteAccessibilityService : AccessibilityService() {
 private val allowed=setOf("AEROCLUBE","BESSA","JARDIM OCEANIA","MANAIRA")
 private var blockedUntil=0L
 override fun onAccessibilityEvent(event: AccessibilityEvent?) {
  if(SystemClock.elapsedRealtime()<blockedUntil)return
  val root=rootInActiveWindow?:return
  if(!norm(collectText(root).joinToString(" | ")).contains("ROTAS DISPONIVEIS"))return
  for(buttonNode in root.findAccessibilityNodeInfosByText("Gerar Rota")){
   val card=findCardContainer(buttonNode)?:continue
   val destinations=extractDeliveryNeighborhoods(collectText(card))
   if(destinations.isEmpty()||!destinations.all{it in allowed})continue
   val clickable=findClickable(buttonNode)?:continue
   if(clickable.performAction(AccessibilityNodeInfo.ACTION_CLICK)){blockedUntil=SystemClock.elapsedRealtime()+15_000L;return}
  }
 }
 override fun onInterrupt()=Unit
 private fun extractDeliveryNeighborhoods(texts:List<String>):List<String>{
  val clean=texts.map{it.trim()}.filter{it.isNotBlank()};val result=mutableListOf<String>()
  for(i in clean.indices)if(Regex("""^ENTREGA\s*\d+$""").matches(norm(clean[i]))){
   val next=clean.drop(i+1).firstOrNull{val n=norm(it);n.isNotBlank()&&!n.startsWith("ENTREGA")&&n!="GERAR ROTA"&&!n.startsWith("COLETA")}
   if(next!=null)result+=norm(next)
  }
  return result.distinct()
 }
 private fun findCardContainer(node:AccessibilityNodeInfo):AccessibilityNodeInfo?{
  var current:AccessibilityNodeInfo?=node
  repeat(8){current=current?.parent?:return null;val text=collectText(current!!);if(text.any{norm(it)=="GERAR ROTA"}&&text.any{Regex("""^ENTREGA\s*\d+$""").matches(norm(it))})return current}
  return null
 }
 private fun findClickable(node:AccessibilityNodeInfo?):AccessibilityNodeInfo?{
  var current=node;repeat(6){if(current?.isClickable==true&&current?.isEnabled==true)return current;current=current?.parent};return null
 }
 private fun collectText(node:AccessibilityNodeInfo):List<String>{
  val out=mutableListOf<String>();fun walk(n:AccessibilityNodeInfo?){if(n==null)return;n.text?.toString()?.takeIf{it.isNotBlank()}?.let(out::add);n.contentDescription?.toString()?.takeIf{it.isNotBlank()}?.let(out::add);for(i in 0 until n.childCount)walk(n.getChild(i))};walk(node);return out
 }
 private fun norm(value:String):String=Normalizer.normalize(value,Normalizer.Form.NFD).replace("\p{M}+".toRegex(),"").uppercase(Locale.ROOT).replace("\s+".toRegex()," ").trim()
}
