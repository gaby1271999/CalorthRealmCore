package me.trolking1.calorthrealmcore.menu;

import me.trolking1.calorthrealmcore.menu.accounselector.CreateFreeCharacters;
import me.trolking1.calorthrealmcore.menu.accounselector.CreatePremiumCharacters;

/**
 * Created by Gabriel on 4/9/2017.
 */
public class MenuManager {

    private CreateFreeCharacters createFreeCharacters;
    private CreatePremiumCharacters createPremiumCharacters;

    public MenuManager() {
        createFreeCharacters = new CreateFreeCharacters();
        createPremiumCharacters = new CreatePremiumCharacters();
    }

    public CreateFreeCharacters getCreateFreeCharacters() {
        return createFreeCharacters;
    }

    public CreatePremiumCharacters getCreatePremiumCharacters() {
        return createPremiumCharacters;
    }
}
