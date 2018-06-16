(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('ApplicantStatusDetailController', ApplicantStatusDetailController);

  ApplicantStatusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ApplicantStatus'];

  function ApplicantStatusDetailController($scope, $rootScope, $stateParams, previousState, entity, ApplicantStatus) {
    var vm = this;

    vm.applicantStatus = entity;
    vm.previousState = previousState.name;

    var unsubscribe = $rootScope.$on('recruitsmartApp:applicantStatusUpdate', function (event, result) {
      vm.applicantStatus = result;
    });
    $scope.$on('$destroy', unsubscribe);
  }
})();
