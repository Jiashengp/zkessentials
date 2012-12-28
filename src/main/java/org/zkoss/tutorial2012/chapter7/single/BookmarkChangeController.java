package org.zkoss.tutorial2012.chapter7.single;

import org.zkoss.tutorial2012.services.SidebarPage;
import org.zkoss.tutorial2012.services.SidebarPageConfig;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.BookmarkEvent;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Include;

@VariableResolver(DelegatingVariableResolver.class)
public class BookmarkChangeController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;

	@WireVariable("sidebarPageConfigSingleDesktop")
	SidebarPageConfig pageConfig;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		if (comp.getParent() != null) {
			throw new RuntimeException("A bookmark change listener can only apply on root comp");
		}

		comp.addEventListener("onBookmarkChange", new EventListener<BookmarkEvent>() {
			public void onEvent(BookmarkEvent event) throws Exception {
				String bookmark = event.getBookmark();
				if(bookmark.startsWith("p_")){
					String p = bookmark.substring("p_".length());
					SidebarPage page = pageConfig.getPage(p);
					
					if(page!=null){
						Include include = (Include)Selectors.iterable(getPage(), "#mainContent include").iterator().next();
						include.setSrc(page.getUri());
					}
				}
			}
		});
	}
}
