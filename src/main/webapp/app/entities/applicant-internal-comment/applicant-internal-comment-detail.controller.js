(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('ApplicantInternalCommentDetailController', ApplicantInternalCommentDetailController);

  ApplicantInternalCommentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ApplicantInternalComment'];

  function ApplicantInternalCommentDetailController($scope, $rootScope, $stateParams, previousState, entity, ApplicantInternalComment) {
    var vm = this;

    vm.applicantInternalComment = entity;
    vm.previousState = previousState.name;

    var unsubscribe = $rootScope.$on('recruitsmartApp:applicantInternalCommentUpdate', function (event, result) {
      vm.applicantInternalComment = result;
    });
    $scope.$on('$destroy', unsubscribe);
  }
})();
