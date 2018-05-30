(function() {
    'use strict';

    angular
        .module('recruitsmartApp')
        .controller('CompanyDetailController', CompanyDetailController);

    CompanyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Company', 'CompanyComment', 'Address', 'JobOrder', 'HiringContact', 'CompanyInternalComment', 'Activity', 'Skill'];

    function CompanyDetailController($scope, $rootScope, $stateParams, previousState, entity, Company, CompanyComment, Address, JobOrder, HiringContact, CompanyInternalComment, Activity, Skill) {
        var vm = this;

        vm.company = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('recruitsmartApp:companyUpdate', function(event, result) {
            vm.company = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
