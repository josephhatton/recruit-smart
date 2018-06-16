(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('ApplicantCommentDetailController', ApplicantCommentDetailController);

  ApplicantCommentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ApplicantComment'];

  function ApplicantCommentDetailController($scope, $rootScope, $stateParams, previousState, entity, ApplicantComment) {
    var vm = this;

    vm.applicantComment = entity;
    vm.previousState = previousState.name;

    var unsubscribe = $rootScope.$on('recruitsmartApp:applicantCommentUpdate', function (event, result) {
      vm.applicantComment = result;
    });
    $scope.$on('$destroy', unsubscribe);
  }
})();
