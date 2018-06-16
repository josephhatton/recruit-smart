(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('CompanyCommentDetailController', CompanyCommentDetailController);

  CompanyCommentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CompanyComment'];

  function CompanyCommentDetailController($scope, $rootScope, $stateParams, previousState, entity, CompanyComment) {
    var vm = this;

    vm.companyComment = entity;
    vm.previousState = previousState.name;

    var unsubscribe = $rootScope.$on('recruitsmartApp:companyCommentUpdate', function (event, result) {
      vm.companyComment = result;
    });
    $scope.$on('$destroy', unsubscribe);
  }
})();
