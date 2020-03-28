package fr.bretzel.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class NBTTagCompound extends NBTBase
{

    private final Map map = new HashMap<>();

    public NBTTagCompound()
    {
    }

    private static void a(String s, NBTBase nbtbase, DataOutput dataoutput) throws IOException
    {
        dataoutput.writeByte(nbtbase.getTypeId());
        if (nbtbase.getTypeId() != 0)
        {
            dataoutput.writeUTF(s);
            nbtbase.write(dataoutput);
        }
    }

    private static byte a(DataInput datainput, NBTReadLimiter nbtreadlimiter) throws IOException
    {
        return datainput.readByte();
    }

    private static String b(DataInput datainput, NBTReadLimiter nbtreadlimiter) throws IOException
    {
        return datainput.readUTF();
    }

    static NBTBase a(byte b0, String s, DataInput datainput, int i, NBTReadLimiter nbtreadlimiter)
    {
        NBTBase nbtbase = NBTBase.createTag(b0);

        try
        {
            nbtbase.load(datainput, i, nbtreadlimiter);
            return nbtbase;
        } catch (IOException ioexception)
        {
            throw new RuntimeException(ioexception);
        }
    }

    static Map b(NBTTagCompound nbttagcompound)
    {
        return nbttagcompound.map;
    }

    void write(DataOutput dataoutput) throws IOException
    {
        Iterator iterator = this.map.keySet().iterator();

        while (iterator.hasNext())
        {
            String s = (String) iterator.next();
            NBTBase nbtbase = (NBTBase) this.map.get(s);

            a(s, nbtbase, dataoutput);
        }

        dataoutput.writeByte(0);
    }

    void load(DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) throws IOException
    {
        if (i > 512)
        {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
        } else
        {
            this.map.clear();

            byte b0;

            while ((b0 = a(datainput, nbtreadlimiter)) != 0)
            {
                String s = b(datainput, nbtreadlimiter);

                nbtreadlimiter.a(16 * s.length());
                NBTBase nbtbase = a(b0, s, datainput, i + 1, nbtreadlimiter);

                this.map.put(s, nbtbase);
            }

        }
    }

    public Set c()
    {
        return this.map.keySet();
    }

    public byte getTypeId()
    {
        return (byte) 10;
    }

    public void set(String s, NBTBase nbtbase)
    {
        this.map.put(s, nbtbase);
    }

    public void setByte(String s, byte b0)
    {
        this.map.put(s, new NBTTagByte(b0));
    }

    public void setShort(String s, short short0)
    {
        this.map.put(s, new NBTTagShort(short0));
    }

    public void setInt(String s, int i)
    {
        this.map.put(s, new NBTTagInt(i));
    }

    public void setLong(String s, long i)
    {
        this.map.put(s, new NBTTagLong(i));
    }

    public void setFloat(String s, float f)
    {
        this.map.put(s, new NBTTagFloat(f));
    }

    public void setDouble(String s, double d0)
    {
        this.map.put(s, new NBTTagDouble(d0));
    }

    public void setString(String s, String s1)
    {
        this.map.put(s, new NBTTagString(s1));
    }

    public void setByteArray(String s, byte[] abyte)
    {
        this.map.put(s, new NBTTagByteArray(abyte));
    }

    public void setIntArray(String s, int[] aint)
    {
        this.map.put(s, new NBTTagIntArray(aint));
    }

    public void setBoolean(String s, boolean flag)
    {
        this.setByte(s, (byte) (flag ? 1 : 0));
    }

    public NBTBase get(String s)
    {
        return (NBTBase) this.map.get(s);
    }

    public byte b(String s)
    {
        NBTBase nbtbase = (NBTBase) this.map.get(s);

        return nbtbase != null ? nbtbase.getTypeId() : 0;
    }

    public boolean hasKey(String s)
    {
        return this.map.containsKey(s);
    }

    public boolean hasKeyOfType(String s, int i)
    {
        byte b0 = this.b(s);

        if (b0 == i)
        {
            return true;
        } else if (i != 99)
        {
            if (b0 > 0)
            {
            }

            return false;
        } else
        {
            return b0 == 1 || b0 == 2 || b0 == 3 || b0 == 4 || b0 == 5 || b0 == 6;
        }
    }

    public byte getByte(String s)
    {
        try
        {
            return !this.hasKeyOfType(s, 99) ? 0 : ((NBTNumber) this.map.get(s)).f();
        } catch (ClassCastException classcastexception)
        {
            return (byte) 0;
        }
    }

    public short getShort(String s)
    {
        try
        {
            return !this.hasKeyOfType(s, 99) ? 0 : ((NBTNumber) this.map.get(s)).e();
        } catch (ClassCastException classcastexception)
        {
            return (short) 0;
        }
    }

    public int getInt(String s)
    {
        try
        {
            return !this.hasKeyOfType(s, 99) ? 0 : ((NBTNumber) this.map.get(s)).d();
        } catch (ClassCastException classcastexception)
        {
            return 0;
        }
    }

    public long getLong(String s)
    {
        try
        {
            return !this.hasKeyOfType(s, 99) ? 0L : ((NBTNumber) this.map.get(s)).c();
        } catch (ClassCastException classcastexception)
        {
            return 0L;
        }
    }

    public float getFloat(String s)
    {
        try
        {
            return !this.hasKeyOfType(s, 99) ? 0.0F : ((NBTNumber) this.map.get(s)).h();
        } catch (ClassCastException classcastexception)
        {
            return 0.0F;
        }
    }

    public double getDouble(String s)
    {
        try
        {
            return !this.hasKeyOfType(s, 99) ? 0.0D : ((NBTNumber) this.map.get(s)).g();
        } catch (ClassCastException classcastexception)
        {
            return 0.0D;
        }
    }

    public String getString(String s)
    {
        try
        {
            return !this.hasKeyOfType(s, 8) ? "" : ((NBTBase) this.map.get(s)).a_();
        } catch (ClassCastException classcastexception)
        {
            return "";
        }
    }

    public byte[] getByteArray(String s)
    {
        try
        {
            return !this.hasKeyOfType(s, 7) ? new byte[0] : ((NBTTagByteArray) this.map.get(s)).c();
        } catch (ClassCastException classcastexception)
        {
            throw new RuntimeException(classcastexception);
        }
    }

    public int[] getIntArray(String s)
    {
        try
        {
            return !this.hasKeyOfType(s, 11) ? new int[0] : ((NBTTagIntArray) this.map.get(s)).c();
        } catch (ClassCastException classcastexception)
        {
            throw new RuntimeException(classcastexception);
        }
    }

    public NBTTagCompound getCompound(String s)
    {
        try
        {
            return !this.hasKeyOfType(s, 10) ? new NBTTagCompound() : (NBTTagCompound) this.map.get(s);
        } catch (ClassCastException classcastexception)
        {
            throw new RuntimeException(classcastexception);
        }
    }

    public NBTTagList getList(String s, int i)
    {
        try
        {
            if (this.b(s) != 9)
            {
                return new NBTTagList();
            } else
            {
                NBTTagList nbttaglist = (NBTTagList) this.map.get(s);

                return nbttaglist.size() > 0 && nbttaglist.f() != i ? new NBTTagList() : nbttaglist;
            }
        } catch (ClassCastException classcastexception)
        {
            throw new RuntimeException(classcastexception);
        }
    }

    public boolean getBoolean(String s)
    {
        return this.getByte(s) != 0;
    }

    public void remove(String s)
    {
        this.map.remove(s);
    }

    public String toString()
    {
        String s = "{";

        String s1;

        for (Iterator iterator = this.map.keySet().iterator(); iterator.hasNext(); s = s + s1 + ':' + this.map.get(s1) + ',')
        {
            s1 = (String) iterator.next();
        }

        return s + "}";
    }

    public boolean isEmpty()
    {
        return this.map.isEmpty();
    }

    public NBTBase clone()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        Iterator iterator = this.map.keySet().iterator();

        while (iterator.hasNext())
        {
            String s = (String) iterator.next();

            nbttagcompound.set(s, ((NBTBase) this.map.get(s)).clone());
        }

        return nbttagcompound;
    }

    public boolean equals(Object object)
    {
        if (super.equals(object))
        {
            NBTTagCompound nbttagcompound = (NBTTagCompound) object;

            return this.map.entrySet().equals(nbttagcompound.map.entrySet());
        } else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return super.hashCode() ^ this.map.hashCode();
    }

    public void a(NBTTagCompound nbttagcompound)
    {
        Iterator iterator = nbttagcompound.map.keySet().iterator();

        while (iterator.hasNext())
        {
            String s = (String) iterator.next();
            NBTBase nbtbase = (NBTBase) nbttagcompound.map.get(s);

            if (nbtbase.getTypeId() == 10)
            {
                if (this.hasKeyOfType(s, 10))
                {
                    NBTTagCompound nbttagcompound1 = this.getCompound(s);

                    nbttagcompound1.a((NBTTagCompound) nbtbase);
                } else
                {
                    this.set(s, nbtbase.clone());
                }
            } else
            {
                this.set(s, nbtbase.clone());
            }
        }

    }
}