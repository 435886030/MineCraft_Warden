package net.minecraft.server.dedicated;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerHangWatchdog implements Runnable {

   private static final Logger field_180251_a = LogManager.getLogger();
   private final DedicatedServer field_180249_b;
   private final long field_180250_c;
   private static final String __OBFID = "CL_00002634";


   public ServerHangWatchdog(DedicatedServer p_i46310_1_) {
      this.field_180249_b = p_i46310_1_;
      this.field_180250_c = p_i46310_1_.func_175593_aQ();
   }

   public void run() {
      while(this.field_180249_b.func_71278_l()) {
         long var1 = this.field_180249_b.func_175587_aJ();
         long var3 = MinecraftServer.func_130071_aq();
         long var5 = var3 - var1;
         if(var5 > this.field_180250_c) {
            field_180251_a.fatal("A single server tick took " + String.format("%.2f", new Object[]{Float.valueOf((float)var5 / 1000.0F)}) + " seconds (should be max " + String.format("%.2f", new Object[]{Float.valueOf(0.05F)}) + ")");
            field_180251_a.fatal("Considering it to be crashed, server will forcibly shutdown.");
            ThreadMXBean var7 = ManagementFactory.getThreadMXBean();
            ThreadInfo[] var8 = var7.dumpAllThreads(true, true);
            StringBuilder var9 = new StringBuilder();
            Error var10 = new Error();
            ThreadInfo[] var11 = var8;
            int var12 = var8.length;

            for(int var13 = 0; var13 < var12; ++var13) {
               ThreadInfo var14 = var11[var13];
               if(var14.getThreadId() == this.field_180249_b.func_175583_aK().getId()) {
                  var10.setStackTrace(var14.getStackTrace());
               }

               var9.append(var14);
               var9.append("\n");
            }

            CrashReport var16 = new CrashReport("Watching Server", var10);
            this.field_180249_b.func_71230_b(var16);
            CrashReportCategory var17 = var16.func_85058_a("Thread Dump");
            var17.func_71507_a("Threads", var9);
            File var18 = new File(new File(this.field_180249_b.func_71238_n(), "crash-reports"), "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-server.txt");
            if(var16.func_147149_a(var18)) {
               field_180251_a.error("This crash report has been saved to: " + var18.getAbsolutePath());
            } else {
               field_180251_a.error("We were unable to save this crash report to disk.");
            }

            this.func_180248_a();
         }

         try {
            Thread.sleep(var1 + this.field_180250_c - var3);
         } catch (InterruptedException var15) {
            ;
         }
      }

   }

   private void func_180248_a() {
      try {
         Timer var1 = new Timer();
         var1.schedule(new TimerTask() {

            private static final String __OBFID = "CL_00002633";

            public void run() {
               Runtime.getRuntime().halt(1);
            }
         }, 10000L);
         System.exit(1);
      } catch (Throwable var2) {
         Runtime.getRuntime().halt(1);
      }

   }

}
