package a1;

import tage.shapes.ManualObject;

/**
 * This class is a subclass of ManualObject. It is used to create an octahedron.
 * @author Matthew M.
 */
public class Matt2ologyShape extends ManualObject {

    /**
     * The vertices of the octahedron are stored in an array.
     * <ul>
     * <li>Each vertex is a 3D point.</li>
     * <li>The vertices are stored in the order of the faces.</li>
     * </ul>
     */
    private float[] vertices = new float[] {
        -1.0f, 0f,  1.0f,    1.0f, 0f,  1.0f,   0.0f, 1.5f, 0.0f,
         1.0f, 0f,  1.0f,    1.0f, 0f, -1.0f,   0.0f, 1.5f, 0.0f,
         1.0f, 0f, -1.0f,   -1.0f, 0f, -1.0f,   0.0f, 1.5f, 0.0f,
        -1.0f, 0f, -1.0f,   -1.0f, 0f,  1.0f,   0.0f, 1.5f, 0.0f,

         1.0f, 0f, -1.0f,   -1.0f, 0f, -1.0f,   0.0f, -1.5f, 0.0f,
        -1.0f, 0f, -1.0f,   -1.0f, 0f,  1.0f,   0.0f, -1.5f, 0.0f,
        -1.0f, 0f,  1.0f,    1.0f, 0f,  1.0f,   0.0f, -1.5f, 0.0f,
         1.0f, 0f,  1.0f,    1.0f, 0f, -1.0f,   0.0f, -1.5f, 0.0f };

    /**
     * The texture coordinates of the octahedron are stored in an array.
     * <ul>
     * <li>Each texture coordinate is a 2D point. </li>
     * <li>The texture coordinates are stored in the order of the faces. </li>
     * <li>The texture coordinates are the same for each face because the texture is a solid color. </li>
     * </ul>
     */
    private float[] texCoords = new float[] {
        0.0f, 0.0f,   1.0f, 0.0f,   0.5f, 1.0f,
        0.0f, 0.0f,   1.0f, 0.0f,   0.5f, 1.0f,
        0.0f, 0.0f,   1.0f, 0.0f,   0.5f, 1.0f,
        0.0f, 0.0f,   1.0f, 0.0f,   0.5f, 1.0f,

        0.0f, 0.0f,   1.0f, 0.0f,   0.5f, 1.0f,
        0.0f, 0.0f,   1.0f, 0.0f,   0.5f, 1.0f,
        0.0f, 0.0f,   1.0f, 0.0f,   0.5f, 1.0f,
        0.0f, 0.0f,   1.0f, 0.0f,   0.5f, 1.0f };

    /**
     * The normals of the octahedron are stored in an array. Each normal is a 3D vector.
     * <ul>
     * <li>The normals are stored in the order of the faces. </li>
     * <li>The normals are the same for each face because the octahedron is a solid object. </li>
     * <li>The normals are used to calculate the lighting of the object. </li>
     * <li>The normals are calculated using the cross product of the vectors of the edges of the face. </li>
     * </ul>
     */
    private float[] normals = new float[] {
         0.0f, 1.0f,  1.0f,    0.0f, 1.0f,  1.0f,    0.0f, 1.0f,  1.0f,
         1.0f, 1.0f,  0.0f,    1.0f, 1.0f,  0.0f,    1.0f, 1.0f,  0.0f,
         0.0f, 1.0f, -1.0f,    0.0f, 1.0f, -1.0f,    0.0f, 1.0f, -1.0f,
        -1.0f, 1.0f,  0.0f,   -1.0f, 1.0f,  0.0f,   -1.0f, 1.0f,  0.0f,

         0.0f, 1.0f,  1.0f,    0.0f, 1.0f,  1.0f,    0.0f, 1.0f,  1.0f,
         1.0f, 1.0f,  0.0f,    1.0f, 1.0f,  0.0f,    1.0f, 1.0f,  0.0f,
         0.0f, 1.0f, -1.0f,    0.0f, 1.0f, -1.0f,    0.0f, 1.0f, -1.0f,
        -1.0f, 1.0f,  0.0f,   -1.0f, 1.0f,  0.0f,   -1.0f, 1.0f,  0.0f };

    /**
     * The constructor of the octahedron.
     * It sets the number of vertices, the vertices, the texture coordinates, and the normals.
     * <ul>
     * <li>The number of vertices is set to 24. </li>
     * <li>The vertices are set to the array of vertices. </li>
     * <li>The texture coordinates are set to the array of texture coordinates. </li>
     * <li>The normals are set to the array of normals. </li>
     * </ul>
     */
    public Matt2ologyShape() {
        super();
        setNumVertices(24); // 6 faces * 4 vertices per face = 24 vertices
        setVertices(vertices);
        setTexCoords(texCoords);
        setNormals(normals);
    }
}