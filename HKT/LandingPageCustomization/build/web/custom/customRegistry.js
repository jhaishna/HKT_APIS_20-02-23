var CustomRegistry = {
    landingPageView: {
        viewExtension: 'text!custom/../../custom/templates/home/landingPageExtensionView.html',
        viewmodel: '../custom/js/viewmodels/home/LandingPageExtensionViewModel'
    },
    router: {
        viewmodel: '../custom/js/viewmodels/router/customRouterViewModel'
    },
    customFullPage: {
        view: 'text!../custom/templates/customFullPage/customFullPageView.html',
        viewmodel: '../custom/js/viewmodels/customFullPage/customFullPageViewModel'
    },
    glidFullPage: {
        view: 'text!../custom/templates/customFullPage/glidFullPageView.html',
        viewmodel: '../custom/js/viewmodels/customFullPage/glidCustomFullPageViewModel'
    },
    makePayment: {
       viewmodel: 'custom/js/viewmodels/payment/configure/CustomMakePayment.js'
    }
};