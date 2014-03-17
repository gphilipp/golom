goog.addDependency("base.js", ['goog'], []);
goog.addDependency("../cljs/core.js", ['cljs.core'], ['goog.string', 'goog.array', 'goog.object', 'goog.string.StringBuffer']);
goog.addDependency("../om/dom.js", ['om.dom'], ['cljs.core']);
goog.addDependency("../om/core.js", ['om.core'], ['cljs.core', 'om.dom', 'goog.ui.IdGenerator']);
goog.addDependency("../clojure/set.js", ['clojure.set'], ['cljs.core']);
goog.addDependency("../golom/core.js", ['golom.core'], ['cljs.core', 'om.core', 'om.dom', 'clojure.set']);