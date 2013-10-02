package br.unesp.lbbc.util;

import java.math.BigDecimal;


public class SurfSmooth {


	private int ultimoMetodo = 0; 
	private double[][] zDataMovAv = null;
	private BigDecimal[][] zDataMovAvBD = null;
	private int maWindowWidthx = 0;
	private int maWindowWidthy = 0;     
	private int nPointsX = 0;
	private int nPointsY = 0;
	private int nPoints = 0; 
	private boolean arbprec = false;
	private BigDecimal[][] zBDdata = null;
	private double[] xData = null;
    private double[] yData = null;  
	private double[][] zData = null;
	private BiCubicSpline bcsMovAv = null;
	private boolean calcMovAv = false;   
	private int nSGcoeff = 0; 
	private BigDecimal[] xBDdata = null;
	private BigDecimal[] yBDdata = null; 
	private int sgPolyDeg = 4;
	private int[][] sgCoeffIndices = null;      
	
	
	public SurfSmooth(double[][] z){ 
        int n = z.length;
        this.zData = z;
        this.yData = new double[n];
        for(int i=0; i<n; i++)this.yData[i] = i;
        n = z[0].length;
        this.xData = new double[n];
        for(int i=0; i<n; i++)this.xData[i] = i;
        this.polyIndices();
        this.check();
    }
    
	
	
	  public double[][] movingAverage(int maWindowWidthx, int maWindowWidthy){
		    
	        this.ultimoMetodo = 1;
	        this.zDataMovAv = new double[this.nPointsY][this.nPointsX];
	        this.zDataMovAvBD = new BigDecimal[this.nPointsY][this.nPointsX];

	        // adjust window width to an odd number of points
	        this.maWindowWidthx = this.windowLength(maWindowWidthx);
	        int wwx = (this.maWindowWidthx - 1)/2;
	        this.maWindowWidthy = this.windowLength(maWindowWidthy);
	        int wwy = (this.maWindowWidthy - 1)/2;

	        // Set x window limits
	        int lpx = 0;
	        int upx = 0;
	        int lpy = 0;
	        int upy = 0;
	        for(int i=0; i<this.nPointsX; i++){
	            if(i>=wwx){
	                lpx = i - wwx;
	            }
	            else{
	                lpx = 0;               
	            }
	            if(i<=this.nPointsX-wwx-1){                    
	                upx = i + wwx;
	            }
	            else{
	                upx = this.nPointsX - 1;
	            }
	            int nw1 = upx - lpx + 1;
	            
	            // Set y window limits
	            for(int j=0; j<this.nPointsY; j++){        
	                if(j>=wwy){
	                    lpy = j - wwy;
	                }
	                else{
	                    lpy = 0;               
	                }
	                if(j<=this.nPointsY-wwy-1){                    
	                    upy = j + wwy;
	                }
	                else{
	                    upy = this.nPointsY - 1;
	                }
	                int nw2 = upy - lpy + 1;
	  
	                // Apply moving average window
	                if(this.arbprec){
	                    BigDecimal sumbd = new BigDecimal("0.0");
	                    for(int k1=lpx; k1<=upx; k1++){
	                        for(int k2=lpy; k2<=upy; k2++){
	                            sumbd = sumbd.add(this.zBDdata[k2][k1]);
	                        }
	                    }
	                    String xx = (new Integer(nw1*nw2)).toString();
	                    this.zDataMovAvBD[j][i] = sumbd.divide(new BigDecimal(xx), BigDecimal.ROUND_HALF_UP);
	                    this.zDataMovAv[j][i] = this.zDataMovAvBD[j][i].doubleValue();
	                }
	                else{
	                    double sum = 0.0;
	                    for(int k1=lpx; k1<=upx; k1++){
	                        for(int k2=lpy; k2<=upy; k2++){
	                            sum += this.zData[k2][k1];
	                        }
	                    }             
	                    this.zDataMovAv[j][i] = sum/(nw1*nw2);
	                    String xx = (new Double(this.zDataMovAv[j][i])).toString();
	                    this.zDataMovAvBD[j][i] = new BigDecimal(xx);
	                }
	            }
	        }
	        
	        // Set up interpolation
	        this.bcsMovAv = new BiCubicSpline(this.yData, this.xData, this.zDataMovAv);
	                
	        this.calcMovAv = true;
	        return copy(this.zDataMovAv);
	     
	    }
	    
	    private double[][] copy(double[][] array){
	        if(array==null)return null;
	        int n = array.length;
	        double[][] copy = new double[n][];
	        for(int i=0; i<n; i++){
	            int m = array[i].length;
	            copy[i] = new double[m];
	            for(int j=0; j<m; j++){
	                copy[i][j] = array[i][j];
	            }
	        }
	        return copy;
	    }
	    
	    
	    // Smooth with a moving average window of dimensions maWindowWidth x maWindowWidth
	    public double[][] movingAverage(int maWindowWidth){
	        return this.movingAverage(maWindowWidth, maWindowWidth);   
	    }
	
	
	    private int windowLength(int width){
	        
	        int ww = 0;
	        if(isEven(width)){
	            ww = width+1;
	        }
	        else{
	           ww = width;
	        }
	        return ww;
	    }
	    
	    public static boolean isEven(int x){
            boolean test=false;
            if(x%2 == 0.0D)test=true;
            return test;
        }
	    
	    
	 // Store indices of the fitting polynomial
	    private void polyIndices(){
	        this.nSGcoeff  =  0;
	        for(int i=1; i<=this.sgPolyDeg+1; i++)this.nSGcoeff +=i;
	        this.sgCoeffIndices = new int[this.nSGcoeff][2];
	        int counter = 0;
	        for(int i = 0; i<=this.sgPolyDeg; i++){
	            for(int j =0; j<=this.sgPolyDeg-i; j++){
	                this.sgCoeffIndices[counter][0] = i; 
	                this.sgCoeffIndices[counter++][1] = j;
	            }
	        }    
	    }
	   
	    // Check for correct dimensions,   data, z=f(x,y)
	    private void check(){
	        // array lengths
	        this.nPointsY = this.yData.length;
	        this.nPointsX = this.xData.length;
	        this.nPoints = this.nPointsX*this.nPointsY;
	        int m1 = this.zData.length;
	        int m2 = this.zData[0].length;
	        
	        // Check orientation
	        if(this.nPointsX==m2){
	            if(this.nPointsY!=m1){
	                throw new IllegalArgumentException("The lengths of the x data arrays, " + this.nPointsX + " and " + this.nPointsY + ", do not match the dimensions of the  y data matrix, " + m1 + " and " + m2);       
	            }
	        }
	        else{
	            if(this.nPointsY==m2){
	                if(this.nPointsX!=m1){
	                    throw new IllegalArgumentException("The lengths of the x data arrays, " + this.nPointsX + " and " + this.nPointsY + ", do not match the dimensions of the  y data matrix, " + m1 + " and " + m2);       
	                }
	                else{
	                    this.zData = this.transpose(this.zData);
	                    System.out.println("zData transposed to match the dimensions of the xData and yData");
	                }
	            }
	        }
	   
	        // Create an array of x values as BigDecimal if not already done
	        if(!this.arbprec){
	            this.xBDdata = new BigDecimal[this.nPointsX];
	            this.yBDdata = new BigDecimal[this.nPointsY];
	            for(int i=0; i<this.nPointsX; i++){
	               this.xBDdata[i] = new BigDecimal((new Double(this.xData[i])).toString()); 
	            }
	            for(int i=0; i<this.nPointsY; i++){
	               this.yBDdata[i] = new BigDecimal((new Double(this.yData[i])).toString()); 
	            }
	        }       
	    }    
	    
	 // Transpose  data
	    private double[][] transpose(double[][] yy){
	        int n1 = yy.length;
	        int n2 = yy[0].length;
	        double[][] hold = new double[n2][n1];
	        for(int i=0; i<n1; i++){
	           for(int j=0; j<n2; j++){
	               hold[j][i] = yy[i][j];
	           } 
	        }
	        return hold;
	    }
	    
	    
}
