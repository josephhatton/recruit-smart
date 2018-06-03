(function() {
    'use strict';

    angular
        .module('recruitsmartApp')
        .controller('JobOrderDetailController', JobOrderDetailController);

    JobOrderDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'JobOrder', 'JobOrderComment', 'Address', 'Skill', 'Company', 'JobOrderInternalComment', 'HiringContact', 'Activity'];

    function JobOrderDetailController($scope, $rootScope, $stateParams, previousState, entity, JobOrder, JobOrderComment, Address, Skill, Company, JobOrderInternalComment, HiringContact, Activity) {
        var vm = this;

        vm.jobOrder = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('recruitsmartApp:jobOrderUpdate', function(event, result) {
            vm.jobOrder = result;
        });
        $scope.$on('$destroy', unsubscribe);

        vm.cancelInfo = function () {
            vm.infoEditable = false;
        };
    }
})();
