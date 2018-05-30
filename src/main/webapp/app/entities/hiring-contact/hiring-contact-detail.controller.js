(function() {
    'use strict';

    angular
        .module('recruitsmartApp')
        .controller('HiringContactDetailController', HiringContactDetailController);

    HiringContactDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'HiringContact', 'Company', 'HiringContactComment', 'HiringContactInternalComment', 'JobOrder', 'Activity'];

    function HiringContactDetailController($scope, $rootScope, $stateParams, previousState, entity, HiringContact, Company, HiringContactComment, HiringContactInternalComment, JobOrder, Activity) {
        var vm = this;

        vm.hiringContact = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('recruitsmartApp:hiringContactUpdate', function(event, result) {
            vm.hiringContact = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
