import java.util.*;
import java.lang.*;

/*
 *     all vertices are numbered 0, 1, 2, ....., n
 *     
 *     Edges are of the form (a, b), a and b are String
 *     
 *     Edges are assumed to be udirected, which implies (1,3) is different from Edge (3,1)t
 */

public class DirectedGraph 
{
    /*
     *   No duplicates vertices
     */
    private Set<String> myVertices;
    /*
     *   No duplicates edges
     */
    private Set<Edge> myEdges;

    /**
     * Constructor for objects of class DirectedGraph
     */
    public DirectedGraph(Set<String> vertices)
    {
        myVertices = new HashSet<String>();
        myVertices.addAll(vertices);
        myEdges = new HashSet<Edge>();
    }

    /*
     *     According to http://webwhompers.com/graph-theory.html
     *     
     *     Two vertices are called adjacent if they share a common edge,   (direction f edge not important)
     *     
     *     precondition myVertices.contains(s) == true  && myVertices.contains(t)
     *     
     *     returns true if? vertex s and vertex t share a common edge
     *         yes, you can be adjacent to your self
     */
    public boolean isAdjacent(String s, String t)
    {
        if (myEdges.contains( new Edge(s,t)) || myEdges.contains( new Edge(t,s))) 
        {
            return true; 
        }
        return false;
    }

    private boolean isDirectionAdjacent(String s, String t)
    {
        return myEdges.contains( new Edge(s,t)); 
    }

    public Set<String> getNeighbors(String s)
    {

        Set<String> arr = new HashSet<String>();
        for ( String str : myVertices )
        {
            if ( !s.equals(str) )
            {
                if ( isAdjacent (s,str) )
                {
                    arr.add(str); 
                }

            }

        }
        return arr;
    }

    /*
     *     According to http://webwhompers.com/graph-theory.html
     *     
     *     The neighborhood of a vertex v in a graph G is the set of vertices adjacent to v. 
     *     
     *     precondition myVertices.contains(s) == true
     *     
     *     returns a Set of all neighbors of s
     *     The neighborhood does not include itself.
     */
    private Set<String> getDirectionNeighbors(String s)
    {

        Set<String> arr = new HashSet<String>();
        for ( String str : myVertices )
        {
            if ( !s.equals(str) )
            {
                if ( isDirectionAdjacent (s,str) )
                {
                    arr.add(str); 
                }

            }

        }
        return arr;

        //         Set<String> n = new HashSet<String>();
        //         for ( Edge e : myEdges)
        //         {
        //             if (e.getA().equals( s ))
        //             {
        //                 n.add(e.getB());
        //             }
        // //             if (e.getB().equals( s) )
        // //             {
        // //                 n.add(e.getA());
        // //             }
        //         }   
        //         return n;
    }

    /*
     *   two graphs are equal iff both graphs have the same vertices and the same edges.
     */
    public boolean equals(Object obj)
    {
        DirectedGraph ans = ( DirectedGraph) obj;
        for ( Edge e: myEdges )
        {
            if ( !ans.myEdges.contains(e))
            {
                return false;
            } 
        }
        for ( String s: myVertices )
        {
            if ( !ans.myVertices.contains(s))
            {
                return false;
            } 
        }
        return true;
    }

    /*
     *  precondition
     *    myVertices.contains(s) == false
     *    
     *  postcondition
     *    myVertices.contains(s) == true
     */
    public void addVertex(String s)
    {
        myVertices.add(s);
    }

    /*
     * precondition
     *   for every edge in edges,
     *     myVertices.contains(edge.getA()) == true
     *     && myVertices.contains(edge.getB()) == true
     *   myEdges.contains(edge) == false
     * postcondtion:  every edge in edges is added to myEdges
     */
    public void addEdges(Set<Edge> edges)
    {
        for (Edge e: edges)
        {
            myEdges.add(e);
        }
    }

    /*
     * precondition
     *   myVertices.contains(e.getA()) == true
     *   && myVertices.contains(e.getB()) == true
     *   myEdges.contains(e) == false
     * postcondtion:  myEdges.contains(e) == true
     */
    public void addEdge(Edge e)
    {
        myEdges.add( new Edge( e.getA(), e.getB()));
    }

    /*
     *   see top of page 379 for defintion of loop:
     *   
     *   An edge incident on a single vertex is called a loop.
     */
    public boolean hasLoop()
    {
        for( Edge e : myEdges)
        {
            if ( e.getA() == e.getB() )
            {
                return true;
            }
        }
        return false;
    }

    /*
     *   see top of page 379 for defintion of loop:
     *   
     *   Two Edges associated with the same vertices are said to be parallel edges
     */
    public boolean hasParallelEdges()
    {
        for( Edge e : myEdges)
        {

            if ( myEdges.contains( new Edge( e.getB(), e.getA() ) ))
            {
                if ( e.getA() != e.getB() )
                {
                    return true;
                }
            }

        }
        return false;
    }

    /*
     *  precondition:   myVertices.contains(v) == true
     *  postcondition:  no side effects
     *    returns the number of edges incident on v
     *    
     *    see page 392
     */
    public int getVertexDegree(String v)
    {
        Set<Edge> temp = new HashSet<Edge>();
        for( Edge e: myEdges )
        {
            if ( e.getA() == v )
            {
                temp.add(e);
            }
            if ( e.getB() == v )
            {
                temp.add(e);
            }
        }
        return temp.size();
    }

    /*
     *   see top of page 379 for defintion of loop:
     *   
     *   A vertex not incident on any edge is called an isolated vertex
     *   A vertex incident on itself (i.e., a loop) is NOT isolated
     *   
     *   returns a List of all Isolted Vertices.  If there are not Isolated vertices, return an empty List
     */
    public List<String> getAllIsolatedVertices()
    {
        ArrayList<String> ans = new ArrayList<String>();

        for ( String str : myVertices )
        {
            if (  getVertexDegree(str) == 0 )
            {
                ans.add(str);    
            }
        }
        return ans;
    }

    /*
     *   returns the union DirectGraoh this . and DirectedGraph g
     *   return a DirectedGraph with:
     *        myVertices = union of this.myVertices and g.myVertices
     *        myEdges = union of this.myEdges and g.myEdges
     */
    public DirectedGraph union(DirectedGraph g)
    {
        DirectedGraph ans = new DirectedGraph(new HashSet<String>());
        for ( Edge e : myEdges)
        {
            ans.myEdges.add(e);
        }
        for ( Edge ee : g.myEdges)
        {
            ans.myEdges.add(ee);
        }
        for ( String s : myVertices )
        {
            ans.myVertices.add(s);
        }
        for ( String ss : g.myVertices )
        {
            ans.myVertices.add(ss);
        } 

        return ans;
    }

    /*
     *   returns the intersection DirectGraoh this . and DirectedGraph g
     *   return a DirectedGraph with:
     *        myVertices = intersection of this.myVertices and g.myVertices
     *        myEdges = intersection of this.myEdges and g.myEdges
     */
    public DirectedGraph intersection(DirectedGraph g)
    {
        DirectedGraph ans = new DirectedGraph(new HashSet<String>());
        for ( Edge e : myEdges)
        {
            if ( g.myEdges.contains(e))
            {
                ans.myEdges.add(e);  
            }
        }
        for ( String s : myVertices )
        {
            if ( g.myVertices.contains(s))
            {
                ans.myVertices.add(s);  
            }
        }
        return ans;
    }

    /*
     *    use definition of Bipartite on page 383
     *       intersection is empty
     *       union == this
     *    Each edge in this.myEdges has one vertex in v1 and one vertex in v2   
     *    
     *    returns true if v1 and v2 form a Biparitite of this Directed Graph
     *    
     *    returns false otherwise
     */
    public boolean isBipartite(Set<String> v1, Set<String> v2)
    {
        Set<String> tempV = new HashSet<String>();
        for ( String s : v1 )
        {
            tempV.add(s);
        }
        for ( String s2 : v2 )
        {
            tempV.add(s2);
        }
        if ( tempV.size() != myVertices.size())
            return false;
        for ( String str : tempV )
        {
            if ( !myVertices.contains(str))
                return false;
        }
        for (String ss: v1 )
        {
            if (v2.contains(ss))
                return false;
        }
        for( Edge e: myEdges )
        {
            if ( ! (v1.contains(e.getA()) && !v2.contains(e.getA()) ) )
            {
                if (! (v1.contains(e.getB()) && !v2.contains(e.getB())))
                    return false;
            }
            if (v2.contains(e.getA()) && !v1.contains(e.getA()))
            {
                if (v2.contains(e.getB()) && !v1.contains(e.getB()))
                    return false;
            }
        }
        return true;
    }

    /*
     *   see page 388
     *   
     *   precondition:  myVertices.contains(v) == true  && myVertices.contains(w) == true
     *                  v and w may be the same vertex, i.e. if v.equals(w), return true
     *   
     *   returns true iff there exist a path from v to w of any length
     */
    public boolean hasPath(String v, String w)
    {

        List<String> arr = getAllIsolatedVertices();
        if ( arr.contains(v) || arr.contains(w) )
            return false;
        Set<String>  n = getDirectionNeighbors(v);
        Set<String> temp = new HashSet<String>();
        while (temp.size() != n.size())
        {
            temp.addAll(n);
            for( String str : temp)
            {
                n.addAll(getDirectionNeighbors(str));
                if ( n.contains(w) )
                    return true;
            }
        }
        return false;
    }

    /*
     *   see page 388 for definition
     *   
     *   A graph is connected if given any two vertices v and w, there exist a path from v to w
     */
    public boolean isConnectedGraph()
    {
        for ( String str : myVertices )
        {
            for ( String str2 : myVertices )
            {
                if ( !hasPath( str ,  str2 ) )
                {
                    return false;
                }
            }
        }
        return true;
    }

    /*
     *    See page 391
     *    
     *    precondition:
     *       myVertices.contains(p.get(k)) == true for all k, 0 <= k < p.size()
     *       p.get(k).length() == 1 for all k, 0 <= k < p.size()
     *    postcondition:
     *       myVertices & myEdges are not altered
     *       
     *    returns true iff there is a path from p.get(0) to p.get(p.size()-1) with no repeated Vertices
     *            yes, you need to make sure myEdges contains the Edges required in p
     */
    public boolean isSimplePath(List<String> p)
    {
        Set<String> temp = new HashSet<String>();
        for ( int x = 0; x < p.size(); x++)
        {
            temp.add(p.get(x));
        }
        if ( temp.size() != p.size())
            return false;
        for ( int xx = 0; xx < p.size()-1; xx++)
        {
            if( !myEdges.contains(new Edge(p.get(xx), p.get(xx+1))))
                return false;  
        }
        return true;
    }

    /*
     *    See page 391
     *    
     *    precondition:
     *       c.get(0).equals(c.get(c.size()-1)) == true
     *       myVertices.contains(c.get(k)) == true for all k, 0 <= k < c.size()
     *       c.get(k).length() == 1 for all k, 0 <= k < c.size()
     *    postcondition:
     *       myVertices & myEdges are not altered
     *
     *    returns true iff there is a path of nonzerolength from c.get(0) to c.get(c.size()-1) with no repeated edges
     *            yes, you need to make sure myEdges contains the Edges required in c
     */
    public boolean isCycle(List<String> c)
    {
        if(c.size() == 0 || c.size() == 1)
            return false;
        if (c.get(0) == c.get(c.size()-1))
        {
            if (c.size() == 2 )
                return true;
        }
        Set<Edge> temp = new HashSet<Edge>();
        if ( hasPath(c.get(0), c.get(c.size()-1)))
        {
            for ( int x = 0; x < c.size()-1; x++)
            {
                if( myEdges.contains( new Edge(c.get(x), c.get(x+1))))
                {
                    temp.add(new Edge(c.get(x), c.get(x+1)));
                } 
            }
        }
        else
        {
            return false;
        }
        if( temp.size() == c.size()-1)
            return true;
        return false;

    }

    /*
     *    See page 391
     *    
     *    precondition:
     *       c.get(0).equals(c.get(c.size()-1)) == true
     *       myVertices.contains(c.get(k)) == true for all k, 0 <= k < c.size()
     *       c.get(k).length() == 1 for all k, 0 <= k < c.size()
     *    postconditino:
     *       myVertices & myEdges are not altered
     *
     *    returns true iff there is a cycle from c.get(0) to c.get(c.size()-1) in which, except for beginning and ending vertices, 
     *                     there are no repeated vertices.
     *            yes, you need to make sure myEdges contains the Edges required in c
     */
    public boolean isSimpleCycle(List<String> sc)
    {
        if (!isCycle(sc))
            return false;
        Set<String> temp = new HashSet<String>();
        for ( int x = 0; x < sc.size(); x++)
        {
            temp.add(sc.get(x));
        }
        if( temp.size() != sc.size()-1 )
            return false;
        for ( int xx = 0; xx < sc.size()-1; xx++)
        {
            if( !myEdges.contains(new Edge(sc.get(xx), sc.get(xx+1))))
                return false;  
        }
        return true;
    }

    /*
     *   precondition:
     *       v.length() == 1;
     *       myVertices.contains(v) == true
     *
     *   See page 391 for defintion of a cycle
     *      A cycle (or circuit) is a path of nonzero length from v to v with no repeated edges
     *
     *   return
     *       A String containing a list vertices which form a simple cycle from v to v
     *          The first and last vertice of the String should be v, e.g.:  "v.....v"
     *              and no other vertice shuld be repeated in the String
     *       null if a simple cycle does not exist.
     */
    public String getSimpleCycle(String v)
    {
        return "";
    }
}