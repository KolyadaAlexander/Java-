
public abstract class AbstractFigure {
    private final Player player;

    public AbstractFigure(final Player player) {
        this.player = player;
    }
    public abstract String getPresent();
    public Player getPlayer() {
        return player;
    }
    
}
