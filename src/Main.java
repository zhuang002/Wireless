import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Main {

	static int m,n,k;
	static HashMap<Integer, ArrayList<Point>> points=new HashMap<>();
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		m = Integer.parseInt(reader.readLine());
		n = Integer.parseInt(reader.readLine());
		k = Integer.parseInt(reader.readLine());
		
		
		
		
		for (int i=0;i<k;i++) {
			String line = reader.readLine();
			String[] parts = line.split(" ");
			HashMap<Integer,ArrayList<Point>> affectedPoints=getAffectedPoints2(Integer.parseInt(parts[0])-1,Integer.parseInt(parts[1])-1,
					Integer.parseInt(parts[2]),Integer.parseInt(parts[3]));
			for (int key:affectedPoints.keySet()) {
				if (points.containsKey(key)) {
					points.get(key).addAll(affectedPoints.get(key));
				} else {
					points.put(key, affectedPoints.get(key));
				}
			}
		}
		
		for (Integer key:points.keySet()) {
			Collections.sort(points.get(key), (x,y)->{
				if (x.x==y.x) {
					return y.bChange-x.bChange;
				}
				return x.x-y.x;
			});
		}
		
		
		int maxBandWidth=0;
		int maxShops=0;
		
		for (Integer key:points.keySet()) {
			ArrayList<Point> list = points.get(key);
			int currentB = 0;
			int previousIdx=0;
			for (int i=0;i<list.size();i++) {
				Point p = list.get(i);
				if (maxBandWidth<currentB && p.x>0) {
					maxBandWidth=currentB;
					maxShops = p.x-previousIdx;
				} else if (maxBandWidth==currentB && p.x>0) {
					maxShops+=p.x-previousIdx;
				}
				previousIdx=p.x;
				currentB+=p.bChange;
			}
		}

		System.out.println(maxBandWidth);
		System.out.println(maxShops);
			
	}
	private static HashMap<Integer, ArrayList<Point>> getAffectedPoints(int cX, int cY, int r, int b) {
		// TODO Auto-generated method stub
		HashMap<Integer,ArrayList<Point>> pointsReturn=new HashMap<>();
		int top = -r+cY;
		if (top<0) top=0;
		int buttom = r+cY;
		if (top>=m) buttom=m-1;
		
		for (int y=top;y<=buttom;y++) {
			ArrayList<Point> linePoints = new ArrayList<>();
			int dy = y-cY;
			int dx = (int)Math.round(Math.sqrt(r*r-dy*dy));
			if (dx*dx+dy*dy>r*r) {
				dx-=1;
			}
			if (dx<0) continue;
			
			int x=-dx+cX;
			if (x<0) x=0;
			linePoints.add(new Point(x,b));
			
			x=dx+cX;
			if (x>=n) x=n-1;
			linePoints.add(new Point(x+1,-b));
			pointsReturn.put(y,linePoints);
		}
		return pointsReturn;
	}
	
	private static HashMap<Integer, ArrayList<Point>> getAffectedPoints2(int cX, int cY, int r, int b) {
		// TODO Auto-generated method stub
		HashMap<Integer,ArrayList<Point>> pointsReturn=new HashMap<>();
		int left = -r+cX;
		if (left<0) left=0;
		int right = r+cX;
		if (left>=n) right=n-1;
		
		for (int x=left;x<=right;x++) {
			ArrayList<Point> linePoints = new ArrayList<>();
			int dx = x-cX;
			int dy = (int)Math.round(Math.sqrt(r*r-dx*dx));
			if (dx*dx+dy*dy>r*r) {
				dy-=1;
			}
			if (dy<0) continue;
			
			int y=-dy+cY;
			if (y<0) y=0;
			linePoints.add(new Point(y,b));
			
			y=dy+cY;
			if (y>=m) y=m-1;
			linePoints.add(new Point(y+1,-b));
			pointsReturn.put(x,linePoints);
		}
		return pointsReturn;
	}

}

class Point {
	int x=0;
	int bChange=0;
	
	public Point(int x, int bchg) {
		this.x=x;
		this.bChange=bchg;
	}
}


