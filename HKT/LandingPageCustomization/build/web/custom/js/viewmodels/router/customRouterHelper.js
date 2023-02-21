define(['jquery', 'knockout'], function ($, ko) {
    var customRouterHelper = {
        openCustomFullPage: function () {
            require(['underscore', CustomRegistry.customFullPage.view,
                CustomRegistry.customFullPage.viewmodel
            ], function (_, view, viewModel) {
                var template = _.template(view);

                // DOM Node on index.html which will hold this view 
                $("#paymentSuspenseSearchContent").append(template);
                var vm = new viewModel();
                ko.cleanNode(document.getElementById('paymentSuspenseSearchContent'));
                ko.applyBindings(vm,
                        document.getElementById("paymentSuspenseSearchContent"));

                // Hide view of landing page before custom full page is loaded
                $('#landingPageContent').empty();
                $("#paymentSuspenseSearchContent").show();
            });
        },
        
        openGLIDCustomFullPage: function () {
            require(['underscore', CustomRegistry.glidFullPage.view,
                CustomRegistry.glidFullPage.viewmodel
            ], function (_, view, viewModel) {
                var template = _.template(view);

                // DOM Node on index.html which will hold this view 
                $("#paymentSuspenseSearchContent").append(template);
                var vm = new viewModel();
                ko.cleanNode(document.getElementById('paymentSuspenseSearchContent'));
                ko.applyBindings(vm,
                        document.getElementById("paymentSuspenseSearchContent"));

                // Hide view of landing page before custom full page is loaded
                $('#landingPageContent').empty();
                $("#paymentSuspenseSearchContent").show();
            });
        }
    };
    return customRouterHelper;
});