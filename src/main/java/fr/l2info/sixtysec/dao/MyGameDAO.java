package fr.l2info.sixtysec.dao;

import fr.l2info.sixtysec.classes.Game;

public interface MyGameDAO {
    void create(Game game);
    void update(Game game);
    void delete(Game game);
    Game getGame();
}
