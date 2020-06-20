package five;

/**
 * 
 * @author asus
 *	悔棋功能
 */
public class UndoX {
	//悔棋最大次数
	private static final int MAXCOUNT = 3;
	//当前所剩悔棋次数
	public int nowCount = 3;
	//为防止连续多次点击悔棋干扰对方，设置时间保护机制
	private Long lastTimestamp;
	//判断是不是游戏刚开始，还没下棋子，防止还没开始就点击悔棋
	private Boolean zeroChess = true;
	//防止一方悔棋之后，另一方又悔棋
	private Boolean continueUx = false;
	
	//防止恶意干扰，判断时间是否连续
	public Boolean spiteDisturb() {
		Long nowTimestamp = System.currentTimeMillis();
		if(lastTimestamp == null) {
			lastTimestamp = nowTimestamp;
		}else {
			Long time = nowTimestamp-lastTimestamp;
			lastTimestamp = nowTimestamp;
			if(time <= 2000) {
				return false;
			}
		}
		return true;
	}
	
	public Boolean getZeroChess() {
		return zeroChess;
	}
	
	public void setZeroChess(Boolean flag) {
		this.zeroChess = flag;
	}

	public Boolean getContinueUx() { return continueUx; }

	public void setContinueUx(Boolean flag) { this.continueUx = flag; }

	public void initUndox(){
		this.lastTimestamp = null;
		this.nowCount = MAXCOUNT;
		this.zeroChess = true;
		this.continueUx = false;
	}
	
	

}
