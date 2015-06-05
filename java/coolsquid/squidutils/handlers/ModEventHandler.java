package coolsquid.squidutils.handlers;

import java.io.File;

import coolsquid.squidapi.event.CrashReportEvent;
import coolsquid.squidapi.util.io.IOUtils;
import coolsquid.squidutils.SquidUtils;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ModEventHandler {

	@SubscribeEvent
	public void onCrashReportFormat(CrashReportEvent.FormatCommentEvent event) {
		File file = new File(SquidUtils.COMMON.getConfigDirectory(), "CrashReportComment.txt");
		if (file.exists()) {
			event.getCommentBuilder().delete(0, event.getCommentBuilder().length())
			.append(IOUtils.readAll(file));
		}
	}
}