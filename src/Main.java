import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Main {

	static int m,n,k;
	static int[][] points;
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		m = Integer.parseInt(reader.readLine());
		n = Integer.parseInt(reader.readLine());
		k = Integer.parseInt(reader.readLine());
		
		points = new int[m][n];
		
		
		for (int i=0;i<k;i++) {
			String line = reader.readLine();
			String[] parts = line.split(" ");
			getAffectedPoints(Integer.parseInt(parts[0])-1,Integer.parseInt(parts[1])-1,
					Integer.parseInt(parts[2]),Integer.parseInt(parts[3]));
		}
		
		
		
		int maxBandWidth=0;
		int maxShops=0;
		
		for (int i=0;i<m;i++) {
			for (int j=1;j<n;j++) {
				points[i][j] += points[i][j-1];
			}
		}
		
		for (int i=0;i<m;i++) {
			for (int j=0;j<n;j++) {
				if (maxBandWidth<points[i][j]) {
					maxBandWidth=points[i][j];
					maxShops=1;
				} else if (maxBandWidth==points[i][j]){
					maxShops++;
				}
			}
		}

		System.out.println(maxBandWidth);
		System.out.println(maxShops);
			
	}
	private static void getAffectedPoints(int cX, int cY, int r, int b) {
		// TODO Auto-generated method stub
		
		int top = -r+cY;
		if (top<0) top=0;
		int bottom = r+cY;
		if (bottom>=m) bottom=m-1;
		
		for (int y=top;y<=bottom;y++) {
			int dy = y-cY;
			int dx = (int)Math.round(Math.sqrt(r*r-dy*dy));
			if (dx*dx+dy*dy>r*r) {
				dx-=1;
			}
			if (dx<0) continue;
			
			int x=-dx+cX;
			if (x<0) x=0;
			points[y][x]+=b;
			
			x=dx+cX;
			if (x<n-1)
				points[y][x+1]+=-b;
		
		}
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


