package com.edsoft.vrcomande.core.networkutility;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * Created by EDSoft on 10/12/2015.
 */
public class TLVParser {

    public static int byteArrayToInt(byte[] paramArrayOfByte, int paramInt)
    {
        int j = 0;
        int i = 0;
        while (i < 4)
        {
            j += ((paramArrayOfByte[(i + paramInt)] & 0xFF) << (3 - i) * 8);
            i += 1;
        }
        return j;
    }

    public static byte[] hexStringToByteArray(String paramString)
    {
        int j = paramString.length();
        byte[] arrayOfByte = new byte[j / 2];
        int i = 0;
        while (i < j)
        {
            arrayOfByte[(i / 2)] = ((byte)((Character.digit(paramString.charAt(i), 16) << 4) + Character.digit(paramString.charAt(i + 1), 16)));
            i += 2;
        }
        return arrayOfByte;
    }

    public static final byte[] intToByteArray(int paramInt)
    {
        return new byte[] { (byte)(paramInt >>> 24), (byte)(paramInt >>> 16), (byte)(paramInt >>> 8), (byte)paramInt };
    }

    public static byte[] readTLV(InputStream paramInputStream, int paramInt)
    {
        if (paramInputStream == null) {
            throw new IllegalArgumentException("Invalid TLV");
        }
        Object localObject2 = null;
        byte[] arrayOfByte = null;
        Object localObject1 = arrayOfByte;
        for (;;)
        {
            int i;
            int j;
            try
            {
                i = paramInputStream.read();
                localObject1 = localObject2;
                if (i == 1) {
                    break label290;
                }
                localObject1 = localObject2;
                if (i != paramInt) {
                    break label290;
                }
                localObject1 = arrayOfByte;
                localObject2 = ByteBuffer.allocate(4);
                localObject1 = arrayOfByte;
                paramInt = paramInputStream.read();
                if (paramInt != -1)
                {
                    localObject1 = arrayOfByte;
                    ((ByteBuffer)localObject2).put((byte)paramInt);
                }
                localObject1 = arrayOfByte;
                paramInt = paramInputStream.read();
                if (paramInt != -1)
                {
                    localObject1 = arrayOfByte;
                    ((ByteBuffer)localObject2).put((byte)paramInt);
                }
                localObject1 = arrayOfByte;
                paramInt = paramInputStream.read();
                if (paramInt != -1)
                {
                    localObject1 = arrayOfByte;
                    ((ByteBuffer)localObject2).put((byte)paramInt);
                }
                localObject1 = arrayOfByte;
                paramInt = paramInputStream.read();
                if (paramInt != -1)
                {
                    localObject1 = arrayOfByte;
                    ((ByteBuffer)localObject2).put((byte)paramInt);
                }
                localObject1 = arrayOfByte;
                j = ((ByteBuffer)localObject2).getInt(0);
                localObject1 = arrayOfByte;
                arrayOfByte = new byte[j];
                paramInt = j;
                i = 1024;
                if (1024 > paramInt) {
                    i = paramInt;
                }
                n = 0;
                localObject1 = arrayOfByte;
                localObject2 = new byte[j];
                k = i;
                m = paramInt;
            }
            catch (Exception paramInputStream)
            {
                int i1;
                paramInputStream.getMessage();
                paramInputStream.printStackTrace();
            }
            localObject1 = arrayOfByte;
            i1 = paramInputStream.read((byte[])localObject2, 0, paramInt);
            int k = paramInt;
            int m = i;
            int n = j;
            if (i1 > 0)
            {
                localObject1 = arrayOfByte;
                System.arraycopy(localObject2, 0, arrayOfByte, j, i1);
                m = j + i1;
                k = i - i1;
                i = k;
                j = m;
                if (paramInt > k)
                {
                    paramInt = k;
                    i = k;
                    j = m;
                }
            }
            else
            {
                label290:
                do
                {
                    return (byte[])localObject1;
                    localObject1 = arrayOfByte;
                } while (m <= 0);
                paramInt = k;
                i = m;
                j = n;
            }
        }
    }

    public static byte[][] readTLV(String paramString, int paramInt)
    {
        return readTLV(hexStringToByteArray(paramString), paramInt);
    }

    /* Error */
    public static byte[][] readTLV(byte[] paramArrayOfByte, int paramInt)
    {
        // Byte code:
        //   0: aload_0
        //   1: ifnull +9 -> 10
        //   4: aload_0
        //   5: arraylength
        //   6: iconst_1
        //   7: if_icmpge +13 -> 20
        //   10: new 37	java/lang/IllegalArgumentException
        //   13: dup
        //   14: ldc 39
        //   16: invokespecial 42	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
        //   19: athrow
        //   20: new 87	java/util/ArrayList
        //   23: dup
        //   24: invokespecial 88	java/util/ArrayList:<init>	()V
        //   27: astore 4
        //   29: aconst_null
        //   30: astore_3
        //   31: new 90	java/io/ByteArrayInputStream
        //   34: dup
        //   35: aload_0
        //   36: invokespecial 93	java/io/ByteArrayInputStream:<init>	([B)V
        //   39: astore_2
        //   40: aload_2
        //   41: invokevirtual 94	java/io/ByteArrayInputStream:read	()I
        //   44: istore 5
        //   46: iload 5
        //   48: iconst_m1
        //   49: if_icmpeq +136 -> 185
        //   52: iload 5
        //   54: iload_1
        //   55: if_icmpne -15 -> 40
        //   58: iconst_4
        //   59: invokestatic 53	java/nio/ByteBuffer:allocate	(I)Ljava/nio/ByteBuffer;
        //   62: astore_0
        //   63: aload_2
        //   64: invokevirtual 94	java/io/ByteArrayInputStream:read	()I
        //   67: istore 5
        //   69: iload 5
        //   71: iconst_m1
        //   72: if_icmpeq +11 -> 83
        //   75: aload_0
        //   76: iload 5
        //   78: i2b
        //   79: invokevirtual 57	java/nio/ByteBuffer:put	(B)Ljava/nio/ByteBuffer;
        //   82: pop
        //   83: aload_2
        //   84: invokevirtual 94	java/io/ByteArrayInputStream:read	()I
        //   87: istore 5
        //   89: iload 5
        //   91: iconst_m1
        //   92: if_icmpeq +11 -> 103
        //   95: aload_0
        //   96: iload 5
        //   98: i2b
        //   99: invokevirtual 57	java/nio/ByteBuffer:put	(B)Ljava/nio/ByteBuffer;
        //   102: pop
        //   103: aload_2
        //   104: invokevirtual 94	java/io/ByteArrayInputStream:read	()I
        //   107: istore 5
        //   109: iload 5
        //   111: iconst_m1
        //   112: if_icmpeq +11 -> 123
        //   115: aload_0
        //   116: iload 5
        //   118: i2b
        //   119: invokevirtual 57	java/nio/ByteBuffer:put	(B)Ljava/nio/ByteBuffer;
        //   122: pop
        //   123: aload_2
        //   124: invokevirtual 94	java/io/ByteArrayInputStream:read	()I
        //   127: istore 5
        //   129: iload 5
        //   131: iconst_m1
        //   132: if_icmpeq +11 -> 143
        //   135: aload_0
        //   136: iload 5
        //   138: i2b
        //   139: invokevirtual 57	java/nio/ByteBuffer:put	(B)Ljava/nio/ByteBuffer;
        //   142: pop
        //   143: aload_0
        //   144: iconst_0
        //   145: invokevirtual 61	java/nio/ByteBuffer:getInt	(I)I
        //   148: istore 5
        //   150: iload 5
        //   152: newarray <illegal type>
        //   154: astore_0
        //   155: aload_2
        //   156: aload_0
        //   157: iconst_0
        //   158: iload 5
        //   160: invokevirtual 95	java/io/ByteArrayInputStream:read	([BII)I
        //   163: pop
        //   164: aload 4
        //   166: aload_0
        //   167: invokevirtual 99	java/util/ArrayList:add	(Ljava/lang/Object;)Z
        //   170: pop
        //   171: goto -131 -> 40
        //   174: astore_0
        //   175: aload_2
        //   176: ifnull +7 -> 183
        //   179: aload_2
        //   180: invokevirtual 102	java/io/ByteArrayInputStream:close	()V
        //   183: aload_0
        //   184: athrow
        //   185: aload_2
        //   186: ifnull +7 -> 193
        //   189: aload_2
        //   190: invokevirtual 102	java/io/ByteArrayInputStream:close	()V
        //   193: aload 4
        //   195: invokevirtual 105	java/util/ArrayList:size	()I
        //   198: anewarray 107	[B
        //   201: astore_0
        //   202: aload 4
        //   204: aload_0
        //   205: invokevirtual 111	java/util/ArrayList:toArray	([Ljava/lang/Object;)[Ljava/lang/Object;
        //   208: pop
        //   209: aload_0
        //   210: areturn
        //   211: astore_0
        //   212: goto -19 -> 193
        //   215: astore_2
        //   216: goto -33 -> 183
        //   219: astore_0
        //   220: aload_3
        //   221: astore_2
        //   222: goto -47 -> 175
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	225	0	paramArrayOfByte	byte[]
        //   0	225	1	paramInt	int
        //   39	151	2	localByteArrayInputStream	java.io.ByteArrayInputStream
        //   215	1	2	localIOException	java.io.IOException
        //   221	1	2	localObject1	Object
        //   30	191	3	localObject2	Object
        //   27	176	4	localArrayList	java.util.ArrayList
        //   44	115	5	i	int
        // Exception table:
        //   from	to	target	type
        //   40	46	174	finally
        //   58	69	174	finally
        //   75	83	174	finally
        //   83	89	174	finally
        //   95	103	174	finally
        //   103	109	174	finally
        //   115	123	174	finally
        //   123	129	174	finally
        //   135	143	174	finally
        //   143	171	174	finally
        //   189	193	211	java/io/IOException
        //   179	183	215	java/io/IOException
        //   31	40	219	finally
    }

    public static void reverse(byte[] paramArrayOfByte)
    {
        if (paramArrayOfByte == null) {}
        for (;;)
        {
            return;
            int j = 0;
            int k = paramArrayOfByte.length - 1;
            while (k > j)
            {
                int i = paramArrayOfByte[k];
                paramArrayOfByte[k] = paramArrayOfByte[j];
                paramArrayOfByte[j] = i;
                k -= 1;
                j += 1;
            }
        }
    }

    public static String toUTF8(String paramString)
    {
        String str1 = paramString;
        String str2 = str1;
        if (paramString != null)
        {
            str2 = str1;
            if (paramString.equals("")) {}
        }
        try
        {
            str2 = new String(paramString.getBytes(), "UTF-8");
            return str2;
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException)
        {
            System.out.println("UnsupportedEncodingException is: " + localUnsupportedEncodingException.getMessage());
        }
        return paramString;
    }

}
