(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('SkillController', SkillController);

  SkillController.$inject = ['Skill', 'SkillSearch'];

  function SkillController(Skill, SkillSearch) {

    var vm = this;

    vm.skills = [];
    vm.clear = clear;
    vm.search = search;
    vm.loadAll = loadAll;

    loadAll();

    function loadAll() {
      Skill.query(function (result) {
        vm.skills = result;
        vm.searchQuery = null;
      });
    }

    function search() {
      if (!vm.searchQuery) {
        return vm.loadAll();
      }
      SkillSearch.query({query: vm.searchQuery}, function (result) {
        vm.skills = result;
        vm.currentSearch = vm.searchQuery;
      });
    }

    function clear() {
      vm.searchQuery = null;
      loadAll();
    }
  }
})();
