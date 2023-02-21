// Copyright (c) 2019 Oracle and/or its affiliates. All rights reserved.
define(['jquery', 'underscore', 'knockout',
    'viewmodels/home/LandingPageViewModel',
    CustomRegistry.landingPageView.viewExtension
], function ($, _, ko, LandingPageViewModel, ExtensionView) {
    function LandingPageExtensionViewModel() {
        LandingPageViewModel.apply(this, arguments);
        var self = this;

        //// Wait until default view is loaded ////
        $('#intro-modal-container span.intro-button-border').ready(function () {
            var extensionView = _.template(ExtensionView);
            $('#intro-modal-container span.intro-button-border').append(extensionView);
            var landPageRootElem = $('#landingPageContent').get(0).firstChild;
            ko.cleanNode(landPageRootElem);
            ko.applyBindings(self, landPageRootElem);
        });

        self.openFullPageView = function (data, event) {
            location.hash = 'customFullPage';
        };
        
        self.openFullPageViewOnKeyPress = function (data, event) {
            if (util.spaceOrEnterHandleEvent(event)) {
                self.openFullPageView(data, event);
            }
        };
        
        self.openGLIDFullPageView = function (data, event) {
            location.hash = 'glidFullPage';
        };
        
        self.openGLIDFullPageViewOnKeyPress = function (data, event) {
            if (util.spaceOrEnterHandleEvent(event)) {
                self.openGLIDFullPageView(data, event);
            }
        };
    }
    LandingPageExtensionViewModel.prototype = new LandingPageViewModel();
    return LandingPageExtensionViewModel;
});