(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('SkillCategoryDetailController', SkillCategoryDetailController);

  SkillCategoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SkillCategory'];

  function SkillCategoryDetailController($scope, $rootScope, $stateParams, previousState, entity, SkillCategory) {
    var vm = this;

    vm.skillCategory = entity;
    vm.previousState = previousState.name;

    var unsubscribe = $rootScope.$on('recruitsmartApp:skillCategoryUpdate', function (event, result) {
      vm.skillCategory = result;
    });
    $scope.$on('$destroy', unsubscribe);
  }
})();
