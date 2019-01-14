package spqEval.lut;

/** 
===============================================================================
* SpermQEvaluator_.java Version 1.0.4
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation
* (http://www.gnu.org/licenses/gpl.txt )
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public
* License along with this program.  If not, see
* <http://www.gnu.org/licenses/gpl-3.0.html>.
*
* Copyright (C) 2018: Jan N. Hansen and Sebastian Raßmann;
*         research group Biophysical Imaging, Institute of Innate 
Immunity, Bonn, Germany
*         (http://www.iii.uni-bonn.de/en/wachten_lab/).
*
* Funding: DFG priority program SPP 1726 “Microswimmers"
*
* For any questions please feel free to contact me (jan.hansen@uni-bonn.de).
*
* 
=============================================================================== 
*/

public class PhysicsLUT extends LUT {
	
	final static int [] red2 = {47,46,45,44,42,41,40,39,37,36,35,33,32,30,29,27,26,24,23,21,19,18,16,14,12,10,9,7,5,3,1,0,0,0,0,0,0,0,0,0,1,2,3,3,4,5,6,7,8,8,9,10,11,12,13,14,15,16,16,17,18,19,20,21,22,23,24,25,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,23,23,23,23,23,23,23,23,23,23,23,23,23,22,22,22,27,32,37,42,48,53,58,63,69,74,79,85,90,96,101,107,112,118,123,129,134,140,145,148,151,153,156,159,161,164,166,169,172,174,177,180,182,185,188,190,193,196,198,201,204,206,210,213,216,220,222,222,222,222,222,223,223,223,223,223,223,224,224,224,224,224,225,225,225,225,225,225,226,226,226,226,226,227,227,227,227,227,228,228,228,228,228,228,229,229,229,229,229,230,230,230,230,230,230,231,231,231,231,231,232,232,232,232,232,233};
	final static int [] green2 = {2,2,2,2,2,2,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,1,3,5,7,9,11,13,15,18,20,23,26,28,31,33,36,39,41,44,47,49,52,55,58,60,63,66,69,71,74,77,80,82,85,88,91,94,96,99,102,105,108,111,114,116,119,122,125,128,131,134,137,140,143,146,149,152,155,158,161,164,167,170,173,176,179,182,185,188,191,194,198,198,198,198,198,199,199,199,199,199,200,200,200,200,200,201,201,201,201,202,202,202,203,203,203,204,204,204,205,205,205,206,206,206,207,207,207,208,208,208,209,209,209,210,210,210,211,211,211,212,212,212,213,213,213,214,214,214,215,215,215,216,216,216,217,217,217,217,217,218,218,218,218,218,218,219,219,219,219,219,219,219,220,220,220,220,220,220,221,221,221,221,221,221,220,217,214,212,209,206,203,200,197,194,191,188,185,182,178,175,172,169,166,163,160,157,154,151,148,145,141,138,135,132,129,126,122,119,116,113,110,106,103,100,97,94,90,87,84,80,77,74,71,67,64,61,57,54,51,47,44,41,37,34};
	final static int [] blue2 = {119,120,122,123,124,125,126,127,128,129,131,132,133,134,135,136,137,138,139,141,142,143,144,145,146,147,148,149,151,152,153,154,155,156,157,158,160,161,162,163,164,165,166,167,168,169,169,170,171,172,173,174,175,176,177,178,179,180,181,182,183,184,185,186,187,188,189,190,191,191,191,191,192,192,192,192,192,193,193,193,193,193,194,194,194,194,194,195,195,195,195,195,196,196,196,196,196,197,197,197,197,197,198,195,192,190,187,184,181,178,176,173,170,167,165,162,159,156,153,150,148,143,138,133,129,124,119,114,109,104,99,94,89,84,79,74,69,64,59,54,49,44,39,33,28,23,22,22,22,22,22,22,22,22,22,21,21,21,21,21,21,21,21,21,21,21,21,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,16,16,16,16,16,16,16,16,16,16,16,16};
		
	public PhysicsLUT() {	
		super(red2, green2, blue2);				
	}

}
