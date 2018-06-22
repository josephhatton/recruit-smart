(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('ApplicantActivityModalController', ApplicantActivityModalController);

  ApplicantActivityModalController.$inject = ['$scope', '$stateParams', 'Principal', '$uibModalInstance', 'Applicant', 'entity',
    'ActivityAction'];

  function ApplicantActivityModalController($scope, $stateParams, Principal, $uibModalInstance, Applicant, entity,
                                            ActivityAction) {
    var vm = this;

    vm.applicant = entity;
    vm.activityActions = null;
    vm.activity = {};

    load();

    function load() {
      Principal.identity().then(function (account) {
        vm.account = account;
        vm.isAuthenticated = Principal.isAuthenticated;
      });
      vm.activityActions = ActivityAction.query();
    }

    function clear() {
      $uibModalInstance.dismiss('cancel');
    }

    vm.save = function () {
      vm.applicant.applicantActivity = vm.activity
      Applicant.save(vm.activity, onSaveSuccess, onSaveError);
    };

    vm.clear = function () {
      $uibModalInstance.dismiss('cancel');
    };

    var onSaveSuccess = function (result) {
      $scope.$emit('recruitSmartApp:activityUpdate', result);
      $uibModalInstance.close(result);
    };
    var onSaveError = function () {
    };

  }
})();
