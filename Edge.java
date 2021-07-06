//   YES, YOU NEED TO FINISH THIS LAB

/**
 *
 *     all vertices are numbered with Strings of length 1
 *     
 *     Edges are of the form (a, b), a.length() == 1 and b.length() == 1
 *
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Edge
{
    private String a;
    private String b;

    /**
     * Constructor for objects of class Pixel
     */
    public Edge(String x, String y)
    {
        a = x;
        b = y;
    }

    public String getA() 
    { 
        return a;  
    }

    public String getB() 
    { 
        return b;  
    }

    public void setA(String s)
    { 
        a = s; 
    }

    public void setB(String s) 
    { 
        b = s; 
    }

    /*
     *   two edges are equal iff they have the same beginning vertex and same ending vertex
     *   
     *   Edge (v, w) is not equals to Edge (w, v)
     */
    public boolean equals(Object obj)
    {
        Edge o = (Edge) obj;
        return o.getA() == this.getA() && o.getB() == this.getB(); 
    }

    public int hashCode()
    {
        return getA().hashCode() + getB().hashCode();
    }
}