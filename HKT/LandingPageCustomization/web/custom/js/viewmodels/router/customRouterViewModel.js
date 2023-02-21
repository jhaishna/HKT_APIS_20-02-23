define(['viewmodels/router/RouterViewModel',
    'jquery',
    'hashrouter',
    '../router/customRouterHelper',
    'routers/RouterHelper',
    'plugins',
    'signals',
    'gridPlugins'
], function (routerViewModel, $, hashrouter, customRouterHelper, routerHelper) {
    function customRouterViewModel() {
        routerViewModel.apply(this, arguments);
        var self = this;
        var super_initialize = self.initialize;
        self.initialize = function () {
            super_initialize();
            // handler function for opening custom page 
            hashrouter.add({
                order: 0,
                name: 'customFullPage',
                route: 'customFullPage',
                openFunc: customRouterHelper.openCustomFullPage
            });
            
            // handler function for opening custom page 
            hashrouter.add({
                order: 0,
                name: 'glidFullPage',
                route: 'glidFullPage',
                openFunc: customRouterHelper.openGLIDCustomFullPage
            });
        };
    }
    customRouterViewModel.prototype = new routerViewModel();
    return customRouterViewModel;
});
