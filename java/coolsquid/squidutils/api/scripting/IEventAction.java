/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.api.scripting;

import net.minecraft.entity.EntityLivingBase;
import coolsquid.squidutils.util.script.EventInfo;

public interface IEventAction {
	public abstract void run(EntityLivingBase entity, EventInfo info);
	public abstract void init(EventInfo info);
}