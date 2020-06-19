package five;

/**
 * 有关棋子的操作
 * @author asus
 *
 */
public class Chess {
	public int[][] allChess = new int[21][21];//定义棋盘的数组
	public int x;//定义每次下棋的横坐标
	public int y;//定义每次下棋的纵坐标
	
	public void initChess(){
		allChess = null;
		System.gc();
		allChess = new int[21][21];
	}
	
	//当鼠标点击棋盘内的地方是，判断当前位置是否可以下棋
	public Boolean canDown(GameStatus gs){
		if(allChess[x][y] == 0){
			//判断玩家当前是执黑子还是白子
			if(gs.isBlackGo){
				allChess[x][y] = 1;
			}else{
				allChess[x][y] = 2;		
			}
			if(gs.isBlackGo){
				gs.message = "轮到白方";
			}else{
				gs.message = "轮到黑方";					
			}
			//落子权轮替
			gs.isBlackGo = !gs.isBlackGo;
			return true;
		}else{
			return false;
		}
	}
	
	//判断游戏是否结束
	public Boolean checkWin(){
		//获取当前落子颜色
		int color = allChess[x][y];
		if(isCrosswise(color) || isLengthways(color) || isRight(color) || isLeft(color)){
			return true;
		}
		return false;
	}
	
	//判断横向是否连成五子
	public Boolean isCrosswise(int color){
		int keycount = 1;
		for(int i = 1; i < 5; i ++){
			if((x+i)<=20 && color == allChess[x+i][y]){
				keycount++;
				if(keycount >= 5){
					return true;
				}
			}else{
				break;
			}
		}
		for(int i = 1; i < 5; i ++){
			if((x-i)>=0 && color == allChess[x-i][y]){
				keycount++;
				if(keycount >= 5){
					return true;
				}
			}else{
				break;
			}
		}
		return false;
	}
	
	//判断纵向是否连成五子
	public Boolean isLengthways(int color){
		int keycount = 1;
		for(int i = 1; i < 5; i ++){
			if((y+i)<=20 && color == allChess[x][y+i]){
				keycount++;
				if(keycount >= 5){
					return true;
				}
			}else{
				break;
			}
		}
		for(int i = 1; i < 5; i ++){
			if((y-i)>=0 && color == allChess[x][y-i]){
				keycount++;
				if(keycount >= 5){
					return true;
				}
			}else{
				break;
			}
		}
		return false;
	}
	
	//判断 \ 这个方向是否连成五子
	public Boolean isRight(int color){
		int keycount = 1;
		for(int i = 1; i < 5; i ++){
			if((x+i)<=20 && (y+i)<=20 && color == allChess[x+i][y+i]){
				keycount++;
				if(keycount >= 5){
					return true;
				}
			}else{
				break;
			}
		}
		for(int i = 1; i < 5; i ++){
			if((x-i)>=0 && (y-i)>=0 && color == allChess[x-i][y-i]){ 
				keycount++;
				if(keycount >= 5){
					return true;
				}
			}else{
				break;
			}
		}
		return false;
	}
	
	//判断 / 这个方向是否连成五子
	public Boolean isLeft(int color){
		int keycount = 1;
		for(int i = 1; i < 5; i ++){
			if((x+i)<=20 && (y-i)>=0 && color == allChess[x+i][y-i]){
				keycount++;
				if(keycount >= 5){
					return true;
				}
			}else{
				break;
			}
		}
		for(int i = 1; i < 5; i ++){
			if((x-i)>=0 && (y+i)<=20 && color == allChess[x-i][y+i]){ 
				keycount++;
				if(keycount >= 5){
					return true;
				}
			}else{
				break;
			}
		}
		return false;
	}
	
	//悔棋实现，将上次赋值的点归零
	public void makeUndo() {
		allChess[x][y] = 0;
	}
	
	
}
