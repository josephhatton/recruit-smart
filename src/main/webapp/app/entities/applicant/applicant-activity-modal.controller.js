(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('ApplicantActivityModalController', ApplicantActivityModalController);

  ApplicantActivityModalController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Account', 'User'];

  function ApplicantActivityModalController($scope, $stateParams, $uibModalInstance, entity,  Account, User) {
    var vm = this;

    vm.applicant = entity;
    vm.users = User.query();

    vm.load = function () {
    };

    var onSaveSuccess = function (result) {
      $scope.$emit('recruitSmartApp:activityUpdate', result);
      $uibModalInstance.close(result);
    };

    var onSaveError = function () {
    };

    vm.save = function () {
    };

    vm.clear = function () {
      $uibModalInstance.dismiss('cancel');
    };

  }
})();
