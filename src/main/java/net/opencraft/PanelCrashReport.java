
package net.opencraft;

import net.opencraft.util.UnexpectedThrowable;
import java.awt.Panel;

public class PanelCrashReport extends Panel {

    public PanelCrashReport(final UnexpectedThrowable g) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokespecial   java/awt/Panel.<init>:()V
        //     4: aload_0        
        //     5: new             Ljava/awt/Color;
        //     8: dup            
        //     9: ldc             3028036
        //    11: invokespecial   java/awt/Color.<init>:(I)V
        //    14: invokevirtual   PanelCrashReport.setBackground:(Ljava/awt/Color;)V
        //    17: aload_0        
        //    18: new             Ljava/awt/BorderLayout;
        //    21: dup            
        //    22: invokespecial   java/awt/BorderLayout.<init>:()V
        //    25: invokevirtual   PanelCrashReport.setLayout:(Ljava/awt/LayoutManager;)V
        //    28: new             Ljava/io/StringWriter;
        //    31: dup            
        //    32: invokespecial   java/io/StringWriter.<init>:()V
        //    35: astore_2       
        //    36: aload_1        
        //    37: getfield        UnexpectedThrowable.exception:Ljava/lang/Exception;
        //    40: new             Ljava/io/PrintWriter;
        //    43: dup            
        //    44: aload_2        
        //    45: invokespecial   java/io/PrintWriter.<init>:(Ljava/io/Writer;)V
        //    48: invokevirtual   java/lang/Exception.printStackTrace:(Ljava/io/PrintWriter;)V
        //    51: aload_2        
        //    52: invokevirtual   java/io/StringWriter.toString:()Ljava/lang/String;
        //    55: astore_3       
        //    56: ldc             ""
        //    58: astore          4
        //    60: ldc             ""
        //    62: astore          5
        //    64: new             Ljava/lang/StringBuilder;
        //    67: dup            
        //    68: invokespecial   java/lang/StringBuilder.<init>:()V
        //    71: aload           5
        //    73: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    76: ldc             "Generated "
        //    78: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    81: new             Ljava/text/SimpleDateFormat;
        //    84: dup            
        //    85: invokespecial   java/text/SimpleDateFormat.<init>:()V
        //    88: new             Ljava/util/Date;
        //    91: dup            
        //    92: invokespecial   java/util/Date.<init>:()V
        //    95: invokevirtual   java/text/SimpleDateFormat.format:(Ljava/util/Date;)Ljava/lang/String;
        //    98: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   101: ldc             "\n"
        //   103: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   106: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   109: astore          5
        //   111: new             Ljava/lang/StringBuilder;
        //   114: dup            
        //   115: invokespecial   java/lang/StringBuilder.<init>:()V
        //   118: aload           5
        //   120: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   123: ldc             "\n"
        //   125: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   128: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   131: astore          5
        //   133: new             Ljava/lang/StringBuilder;
        //   136: dup            
        //   137: invokespecial   java/lang/StringBuilder.<init>:()V
        //   140: aload           5
        //   142: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   145: ldc             "OpenCraft: OpenCraft Infdev\n"
        //   147: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   150: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   153: astore          5
        //   155: new             Ljava/lang/StringBuilder;
        //   158: dup            
        //   159: invokespecial   java/lang/StringBuilder.<init>:()V
        //   162: aload           5
        //   164: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   167: ldc             "OS: "
        //   169: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   172: ldc             "os.name"
        //   174: invokestatic    java/lang/System.getProperty:(Ljava/lang/String;)Ljava/lang/String;
        //   177: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   180: ldc             " ("
        //   182: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   185: ldc             "os.arch"
        //   187: invokestatic    java/lang/System.getProperty:(Ljava/lang/String;)Ljava/lang/String;
        //   190: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   193: ldc             ") version "
        //   195: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   198: ldc             "os.version"
        //   200: invokestatic    java/lang/System.getProperty:(Ljava/lang/String;)Ljava/lang/String;
        //   203: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   206: ldc             "\n"
        //   208: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   211: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   214: astore          5
        //   216: new             Ljava/lang/StringBuilder;
        //   219: dup            
        //   220: invokespecial   java/lang/StringBuilder.<init>:()V
        //   223: aload           5
        //   225: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   228: ldc             "Java: "
        //   230: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   233: ldc             "java.version"
        //   235: invokestatic    java/lang/System.getProperty:(Ljava/lang/String;)Ljava/lang/String;
        //   238: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   241: ldc             ", "
        //   243: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   246: ldc             "java.vendor"
        //   248: invokestatic    java/lang/System.getProperty:(Ljava/lang/String;)Ljava/lang/String;
        //   251: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   254: ldc             "\n"
        //   256: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   259: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   262: astore          5
        //   264: new             Ljava/lang/StringBuilder;
        //   267: dup            
        //   268: invokespecial   java/lang/StringBuilder.<init>:()V
        //   271: aload           5
        //   273: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   276: ldc             "VM: "
        //   278: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   281: ldc             "java.vm.name"
        //   283: invokestatic    java/lang/System.getProperty:(Ljava/lang/String;)Ljava/lang/String;
        //   286: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   289: ldc             " ("
        //   291: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   294: ldc             "java.vm.info"
        //   296: invokestatic    java/lang/System.getProperty:(Ljava/lang/String;)Ljava/lang/String;
        //   299: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   302: ldc             "), "
        //   304: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   307: ldc             "java.vm.vendor"
        //   309: invokestatic    java/lang/System.getProperty:(Ljava/lang/String;)Ljava/lang/String;
        //   312: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   315: ldc             "\n"
        //   317: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   320: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   323: astore          5
        //   325: new             Ljava/lang/StringBuilder;
        //   328: dup            
        //   329: invokespecial   java/lang/StringBuilder.<init>:()V
        //   332: aload           5
        //   334: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   337: ldc             "LWJGL: "
        //   339: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   342: invokestatic    org/lwjgl/Sys.getVersion:()Ljava/lang/String;
        //   345: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   348: ldc             "\n"
        //   350: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   353: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   356: astore          5
        //   358: sipush          7936
        //   361: invokestatic    org/lwjgl/opengl/GL11.glGetString:(I)Ljava/lang/String;
        //   364: astore          4
        //   366: new             Ljava/lang/StringBuilder;
        //   369: dup            
        //   370: invokespecial   java/lang/StringBuilder.<init>:()V
        //   373: aload           5
        //   375: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   378: ldc             "OpenGL: "
        //   380: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   383: sipush          7937
        //   386: invokestatic    org/lwjgl/opengl/GL11.glGetString:(I)Ljava/lang/String;
        //   389: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   392: ldc             " version "
        //   394: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   397: sipush          7938
        //   400: invokestatic    org/lwjgl/opengl/GL11.glGetString:(I)Ljava/lang/String;
        //   403: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   406: ldc             ", "
        //   408: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   411: sipush          7936
        //   414: invokestatic    org/lwjgl/opengl/GL11.glGetString:(I)Ljava/lang/String;
        //   417: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   420: ldc             "\n"
        //   422: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   425: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   428: astore          5
        //   430: goto            457
        //   433: astore          6
        //   435: new             Ljava/lang/StringBuilder;
        //   438: dup            
        //   439: invokespecial   java/lang/StringBuilder.<init>:()V
        //   442: aload           5
        //   444: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   447: ldc             "[failed to get system properties]\n"
        //   449: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   452: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   455: astore          5
        //   457: new             Ljava/lang/StringBuilder;
        //   460: dup            
        //   461: invokespecial   java/lang/StringBuilder.<init>:()V
        //   464: aload           5
        //   466: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   469: ldc             "\n"
        //   471: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   474: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   477: astore          5
        //   479: new             Ljava/lang/StringBuilder;
        //   482: dup            
        //   483: invokespecial   java/lang/StringBuilder.<init>:()V
        //   486: aload           5
        //   488: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   491: aload_3        
        //   492: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   495: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   498: astore          5
        //   500: ldc             ""
        //   502: astore          6
        //   504: new             Ljava/lang/StringBuilder;
        //   507: dup            
        //   508: invokespecial   java/lang/StringBuilder.<init>:()V
        //   511: aload           6
        //   513: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   516: ldc             "\n"
        //   518: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   521: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   524: astore          6
        //   526: new             Ljava/lang/StringBuilder;
        //   529: dup            
        //   530: invokespecial   java/lang/StringBuilder.<init>:()V
        //   533: aload           6
        //   535: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   538: ldc             "\n"
        //   540: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   543: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   546: astore          6
        //   548: aload_3        
        //   549: ldc             "Pixel format not accelerated"
        //   551: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   554: ifeq            831
        //   557: new             Ljava/lang/StringBuilder;
        //   560: dup            
        //   561: invokespecial   java/lang/StringBuilder.<init>:()V
        //   564: aload           6
        //   566: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   569: ldc             "      Bad video card drivers!      \n"
        //   571: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   574: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   577: astore          6
        //   579: new             Ljava/lang/StringBuilder;
        //   582: dup            
        //   583: invokespecial   java/lang/StringBuilder.<init>:()V
        //   586: aload           6
        //   588: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   591: ldc             "      -----------------------      \n"
        //   593: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   596: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   599: astore          6
        //   601: new             Ljava/lang/StringBuilder;
        //   604: dup            
        //   605: invokespecial   java/lang/StringBuilder.<init>:()V
        //   608: aload           6
        //   610: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   613: ldc             "\n"
        //   615: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   618: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   621: astore          6
        //   623: new             Ljava/lang/StringBuilder;
        //   626: dup            
        //   627: invokespecial   java/lang/StringBuilder.<init>:()V
        //   630: aload           6
        //   632: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   635: ldc             "OpenCraft was unable to start because it failed to find an accelerated OpenGL mode.\n"
        //   637: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   640: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   643: astore          6
        //   645: new             Ljava/lang/StringBuilder;
        //   648: dup            
        //   649: invokespecial   java/lang/StringBuilder.<init>:()V
        //   652: aload           6
        //   654: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   657: ldc             "This can usually be fixed by updating the video card drivers.\n"
        //   659: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   662: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   665: astore          6
        //   667: aload           4
        //   669: invokevirtual   java/lang/String.toLowerCase:()Ljava/lang/String;
        //   672: ldc             "nvidia"
        //   674: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   677: ifeq            749
        //   680: new             Ljava/lang/StringBuilder;
        //   683: dup            
        //   684: invokespecial   java/lang/StringBuilder.<init>:()V
        //   687: aload           6
        //   689: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   692: ldc             "\n"
        //   694: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   697: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   700: astore          6
        //   702: new             Ljava/lang/StringBuilder;
        //   705: dup            
        //   706: invokespecial   java/lang/StringBuilder.<init>:()V
        //   709: aload           6
        //   711: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   714: ldc             "You might be able to find drivers for your video card here:\n"
        //   716: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   719: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   722: astore          6
        //   724: new             Ljava/lang/StringBuilder;
        //   727: dup            
        //   728: invokespecial   java/lang/StringBuilder.<init>:()V
        //   731: aload           6
        //   733: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   736: ldc             "  http://www.nvidia.com/\n"
        //   738: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   741: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   744: astore          6
        //   746: goto            985
        //   749: aload           4
        //   751: invokevirtual   java/lang/String.toLowerCase:()Ljava/lang/String;
        //   754: ldc             "ati"
        //   756: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   759: ifeq            985
        //   762: new             Ljava/lang/StringBuilder;
        //   765: dup            
        //   766: invokespecial   java/lang/StringBuilder.<init>:()V
        //   769: aload           6
        //   771: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   774: ldc             "\n"
        //   776: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   779: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   782: astore          6
        //   784: new             Ljava/lang/StringBuilder;
        //   787: dup            
        //   788: invokespecial   java/lang/StringBuilder.<init>:()V
        //   791: aload           6
        //   793: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   796: ldc             "You might be able to find drivers for your video card here:\n"
        //   798: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   801: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   804: astore          6
        //   806: new             Ljava/lang/StringBuilder;
        //   809: dup            
        //   810: invokespecial   java/lang/StringBuilder.<init>:()V
        //   813: aload           6
        //   815: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   818: ldc             "  http://www.amd.com/\n"
        //   820: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   823: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   826: astore          6
        //   828: goto            985
        //   831: new             Ljava/lang/StringBuilder;
        //   834: dup            
        //   835: invokespecial   java/lang/StringBuilder.<init>:()V
        //   838: aload           6
        //   840: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   843: ldc             "      OpenCraft has crashed!      \n"
        //   845: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   848: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   851: astore          6
        //   853: new             Ljava/lang/StringBuilder;
        //   856: dup            
        //   857: invokespecial   java/lang/StringBuilder.<init>:()V
        //   860: aload           6
        //   862: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   865: ldc             "      ----------------------      \n"
        //   867: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   870: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   873: astore          6
        //   875: new             Ljava/lang/StringBuilder;
        //   878: dup            
        //   879: invokespecial   java/lang/StringBuilder.<init>:()V
        //   882: aload           6
        //   884: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   887: ldc             "\n"
        //   889: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   892: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   895: astore          6
        //   897: new             Ljava/lang/StringBuilder;
        //   900: dup            
        //   901: invokespecial   java/lang/StringBuilder.<init>:()V
        //   904: aload           6
        //   906: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   909: ldc             "OpenCraft has stopped running because it encountered a problem.\n"
        //   911: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   914: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   917: astore          6
        //   919: new             Ljava/lang/StringBuilder;
        //   922: dup            
        //   923: invokespecial   java/lang/StringBuilder.<init>:()V
        //   926: aload           6
        //   928: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   931: ldc             "\n"
        //   933: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   936: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   939: astore          6
        //   941: new             Ljava/lang/StringBuilder;
        //   944: dup            
        //   945: invokespecial   java/lang/StringBuilder.<init>:()V
        //   948: aload           6
        //   950: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   953: ldc             "If you wish to report this, please copy this entire text and email it to support@mojang.com.\n"
        //   955: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   958: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   961: astore          6
        //   963: new             Ljava/lang/StringBuilder;
        //   966: dup            
        //   967: invokespecial   java/lang/StringBuilder.<init>:()V
        //   970: aload           6
        //   972: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   975: ldc             "Please include a description of what you did when the error occured.\n"
        //   977: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   980: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   983: astore          6
        //   985: new             Ljava/lang/StringBuilder;
        //   988: dup            
        //   989: invokespecial   java/lang/StringBuilder.<init>:()V
        //   992: aload           6
        //   994: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   997: ldc             "\n"
        //   999: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1002: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1005: astore          6
        //  1007: new             Ljava/lang/StringBuilder;
        //  1010: dup            
        //  1011: invokespecial   java/lang/StringBuilder.<init>:()V
        //  1014: aload           6
        //  1016: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1019: ldc             "\n"
        //  1021: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1024: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1027: astore          6
        //  1029: new             Ljava/lang/StringBuilder;
        //  1032: dup            
        //  1033: invokespecial   java/lang/StringBuilder.<init>:()V
        //  1036: aload           6
        //  1038: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1041: ldc             "\n"
        //  1043: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1046: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1049: astore          6
        //  1051: new             Ljava/lang/StringBuilder;
        //  1054: dup            
        //  1055: invokespecial   java/lang/StringBuilder.<init>:()V
        //  1058: aload           6
        //  1060: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1063: ldc             "--- BEGIN ERROR REPORT "
        //  1065: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1068: aload           6
        //  1070: invokevirtual   java/lang/String.hashCode:()I
        //  1073: invokestatic    java/lang/Integer.toHexString:(I)Ljava/lang/String;
        //  1076: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1079: ldc             " --------\n"
        //  1081: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1084: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1087: astore          6
        //  1089: new             Ljava/lang/StringBuilder;
        //  1092: dup            
        //  1093: invokespecial   java/lang/StringBuilder.<init>:()V
        //  1096: aload           6
        //  1098: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1101: aload           5
        //  1103: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1106: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1109: astore          6
        //  1111: new             Ljava/lang/StringBuilder;
        //  1114: dup            
        //  1115: invokespecial   java/lang/StringBuilder.<init>:()V
        //  1118: aload           6
        //  1120: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1123: ldc             "--- END ERROR REPORT "
        //  1125: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1128: aload           6
        //  1130: invokevirtual   java/lang/String.hashCode:()I
        //  1133: invokestatic    java/lang/Integer.toHexString:(I)Ljava/lang/String;
        //  1136: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1139: ldc             " ----------\n"
        //  1141: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1144: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1147: astore          6
        //  1149: new             Ljava/lang/StringBuilder;
        //  1152: dup            
        //  1153: invokespecial   java/lang/StringBuilder.<init>:()V
        //  1156: aload           6
        //  1158: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1161: ldc             "\n"
        //  1163: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1166: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1169: astore          6
        //  1171: new             Ljava/lang/StringBuilder;
        //  1174: dup            
        //  1175: invokespecial   java/lang/StringBuilder.<init>:()V
        //  1178: aload           6
        //  1180: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1183: ldc             "\n"
        //  1185: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1188: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1191: astore          6
        //  1193: new             Ljava/awt/TextArea;
        //  1196: dup            
        //  1197: aload           6
        //  1199: iconst_0       
        //  1200: iconst_0       
        //  1201: iconst_1       
        //  1202: invokespecial   java/awt/TextArea.<init>:(Ljava/lang/String;III)V
        //  1205: astore          7
        //  1207: aload           7
        //  1209: new             Ljava/awt/Font;
        //  1212: dup            
        //  1213: ldc             "Monospaced"
        //  1215: iconst_0       
        //  1216: bipush          12
        //  1218: invokespecial   java/awt/Font.<init>:(Ljava/lang/String;II)V
        //  1221: invokevirtual   java/awt/TextArea.setFont:(Ljava/awt/Font;)V
        //  1224: aload_0        
        //  1225: new             LCanvasMojangLogo;
        //  1228: dup            
        //  1229: invokespecial   CanvasMojangLogo.<init>:()V
        //  1232: ldc             "North"
        //  1234: invokevirtual   PanelCrashReport.add:(Ljava/awt/Component;Ljava/lang/Object;)V
        //  1237: aload_0        
        //  1238: new             LCanvasCrashReport;
        //  1241: dup            
        //  1242: bipush          80
        //  1244: invokespecial   CanvasCrashReport.<init>:(I)V
        //  1247: ldc             "East"
        //  1249: invokevirtual   PanelCrashReport.add:(Ljava/awt/Component;Ljava/lang/Object;)V
        //  1252: aload_0        
        //  1253: new             LCanvasCrashReport;
        //  1256: dup            
        //  1257: bipush          80
        //  1259: invokespecial   CanvasCrashReport.<init>:(I)V
        //  1262: ldc             "West"
        //  1264: invokevirtual   PanelCrashReport.add:(Ljava/awt/Component;Ljava/lang/Object;)V
        //  1267: aload_0        
        //  1268: new             LCanvasCrashReport;
        //  1271: dup            
        //  1272: bipush          100
        //  1274: invokespecial   CanvasCrashReport.<init>:(I)V
        //  1277: ldc             "South"
        //  1279: invokevirtual   PanelCrashReport.add:(Ljava/awt/Component;Ljava/lang/Object;)V
        //  1282: aload_0        
        //  1283: aload           7
        //  1285: ldc             "Center"
        //  1287: invokevirtual   PanelCrashReport.add:(Ljava/awt/Component;Ljava/lang/Object;)V
        //  1290: return
        // 
        // throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
