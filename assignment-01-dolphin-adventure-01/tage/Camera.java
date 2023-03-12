package tage;
import org.joml.*;
import org.joml.Math;

/**
* A Camera specifies the rendering viewpoint for a Viewport.
* A Camera instance includes fields for location, and U, V, N vectors.
* It includes look-at() methods for pointing the camera at a given location or object.
* It also includes a method for generating a VIEW matrix.
* <p>
* U, V, and N must form an orthogonal left-handed system of axes indicating the camera orientation.
* Note that this is NOT a camera controller - however, a controller could be written
* for a Camera instance by modifying location, U, V, and N based on user input.
* The default camera position is at (0,0,1) looking down the -Z axis towards the origin.
* @author Scott Gordon
*/

public class Camera
{
	private Vector3f u, v, n;
	private Vector3f defaultU, defaultV, defaultN;
	private Vector3f location, defaultLocation;
	private Matrix4f view, viewR, viewT;

	// used for moveForward() and moveBackward() functions
	private Vector3f directionVectorN, currentLocation, newLocation;

	/**
	 * instantiates a Camera object at location (0,0,1) and facing down the -Z axis
	 * towards the origin
	 */
	public Camera()
	{	defaultLocation = new Vector3f(0.0f, 0.0f, 1.0f);
		defaultU = new Vector3f(1.0f, 0.0f, 0.0f);
		defaultV = new Vector3f(0.0f, 1.0f, 0.0f);
		defaultN = new Vector3f(0.0f, 0.0f, -1.0f);
		location = new Vector3f(defaultLocation);
		u = new Vector3f(defaultU);
		v = new Vector3f(defaultV);
		n = new Vector3f(defaultN);
		view = new Matrix4f();
		viewR = new Matrix4f();
		viewT = new Matrix4f();
	}

	/** sets the world location of this Camera */
	public void setLocation(Vector3f l) { location.set(l); }

	/** sets the U (right-facing) vector for this Camera */
	public void setU(Vector3f newU) { u.set(newU); }

	/** sets the V (upward-facing) vector for this Camera */
	public void setV(Vector3f newV) { v.set(newV); }

	/** sets the N (forward-facing) vector for this Camera */
	public void setN(Vector3f newN) { n.set(newN); }

	/** returns the world location of this Camera */
	public Vector3f getLocation() { return new Vector3f(location); }

	/** gets the U (right-facing) vector for this Camera */
	public Vector3f getU() { return new Vector3f(u); }

	/** gets the V (upward-facing) vector for this Camera */
	public Vector3f getV() { return new Vector3f(v); }

	/** gets the N (forward-facing) vector for this Camera */
	public Vector3f getN() { return new Vector3f(n); }


	/** orients this Camera so that it faces a specified Vector3f world location */
	public void lookAt(Vector3f target) { lookAt(target.x(), target.y(), target.z()); }

	/** orients this Camera so that it faces a specified GameObject */
	public void lookAt(GameObject go) { lookAt(go.getWorldLocation()); }

	/** orients this Camera so that it faces a specified (x,y,z) world location */
	public void lookAt(float x, float y, float z)
	{	setN((new Vector3f(x-location.x(), y-location.y(), z-location.z())).normalize());
		Vector3f copyN = new Vector3f(n);
		if (n.equals(0,1,0))
			u = new Vector3f(1f,0f,0f);
		else
			u = (new Vector3f(copyN.cross(0f,1f,0f))).normalize();
		Vector3f copyU = new Vector3f(u);
		v = (new Vector3f(copyU.cross(n))).normalize();
	}

	protected Matrix4f getViewMatrix()
	{	viewT.set(1.0f, 0.0f, 0.0f, 0.0f,
		0.0f, 1.0f, 0.0f, 0.0f,
		0.0f, 0.0f, 1.0f, 0.0f,
		-location.x(), -location.y(), -location.z(), 1.0f);

		viewR.set(u.x(), v.x(), -n.x(), 0.0f,
		u.y(), v.y(), -n.y(), 0.0f,
		u.z(), v.z(), -n.z(), 0.0f,
		0.0f, 0.0f, 0.0f, 1.0f);

		view.identity();
		view.mul(viewR);
		view.mul(viewT);

		return (view);
	}

	/**
	 * Moves the camera forward in the direction it is facing.
	 * 
	 * @author Matthew M.
	 * @param time
	 */
	public void moveForward(float time, boolean isCameraInAvatarProximity) {
		currentLocation = this.getLocation();
		directionVectorN = this.getN();
		newLocation = currentLocation.add(
				directionVectorN.x(),
				directionVectorN.y(),
				directionVectorN.z());
		if (isCameraInAvatarProximity) {
			this.setLocation(newLocation);
		}
	}

	public void moveBackward(float time, boolean isCameraInAvatarProximity) {
		currentLocation = this.getLocation();
		directionVectorN = this.getN();
		newLocation = currentLocation.add(
				directionVectorN.x(),
				directionVectorN.y(),
				directionVectorN.z());
		if (isCameraInAvatarProximity) {
			this.setLocation(newLocation);
		}
	}

	/**
	 * Pitches the camera up or down.
	 * Positive values pitch up, negative values pitch down.
	 * 
	 * @param movement_speed
	 * @author Matthew M.
	 */
	public void pitch(float movement_speed) {
		Vector3f newU = new Vector3f(); // u' = u * cos(a) - n * sin(a)
		Vector3f newV = new Vector3f(); // v' = v * cos(a) - n * sin(a)
		Vector3f newN = new Vector3f(); // n' = u * sin(a) + n * cos(a)

		newU = u; // u' = u
		newV = v; // v' = v
		newN = n; // n' = n

		// u = u * cos(a) - v * sin(a)
		newU = new Vector3f(u.x() * (float) Math.cos(movement_speed) - v.x() * (float) Math.sin(movement_speed),
				u.y() * (float) Math.cos(movement_speed) - v.y() * (float) Math.sin(movement_speed),
				u.z() * (float) Math.cos(movement_speed) - v.z() * (float) Math.sin(movement_speed));
		// v = u * sin(a) + v * cos(a)
		newV = new Vector3f(u.x() * (float) Math.sin(movement_speed) + v.x() * (float) Math.cos(movement_speed),
				u.y() * (float) Math.sin(movement_speed) + v.y() * (float) Math.cos(movement_speed),
				u.z() * (float) Math.sin(movement_speed) + v.z() * (float) Math.cos(movement_speed));
		// n = u * sin(a) + n * cos(a)
		newN = new Vector3f(u.x() * (float) Math.sin(movement_speed) + n.x() * (float) Math.cos(movement_speed),
				u.y() * (float) Math.sin(movement_speed) + n.y() * (float) Math.cos(movement_speed),
				u.z() * (float) Math.sin(movement_speed) + n.z() * (float) Math.cos(movement_speed));

		u = newU; // u = u'
		v = newV; // v = v'
		n = newN; // n = n'

		// normalize u, v, and n
		u = u.normalize(); // u = u' / |u'|
		v = v.normalize(); // v = v' / |v'|
		n = n.normalize(); // n = n' / |n'|

		// update the view matrix
		viewR.set(u.x(), v.x(), -n.x(), 0.0f,
				  u.y(), v.y(), -n.y(), 0.0f,
				  u.z(), v.z(), -n.z(), 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
		view.identity();
		view.mul(viewR);
		view.mul(viewT);
	}
}